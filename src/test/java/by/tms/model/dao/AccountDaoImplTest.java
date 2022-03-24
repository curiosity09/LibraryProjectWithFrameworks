package by.tms.model.dao;

import by.tms.model.config.HibernateConfigTest;
import by.tms.model.entity.user.Account;
import by.tms.model.entity.user.Admin;
import by.tms.model.entity.user.Librarian;
import by.tms.model.entity.user.User;
import by.tms.util.TestDataImporter;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateConfigTest.class)
@Transactional(readOnly = true)
class AccountDaoImplTest {

    AccountDaoImplTest() {
    }

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void initDb() {
        TestDataImporter.importTestData(sessionFactory);
    }

    @AfterTestMethod
    public void flush() {
        sessionFactory.close();
    }

    @Test
    void findAll() {
        List<Account> results = accountDao.findAll();
        assertEquals(6, results.size());
    }

    @Test
    void findAllUsers() {
        List<User> results = accountDao.findAllUsers();
        assertEquals("user", results.get(0).getUsername());
    }

    @Test
    void findAllAdmins() {
        List<Admin> results = accountDao.findAllAdmins();
        assertEquals("admin", results.get(0).getUsername());
    }

    @Test
    void findAllLibrarians() {
        List<Librarian> results = accountDao.findAllLibrarians();
        assertEquals("lib", results.get(1).getUsername());
    }

    @Test
    void findByUsername() {
        Optional<User> results = accountDao.findUserByUsername("user");
        results.ifPresent(user -> assertEquals("user", user.getUsername()));
    }

    @Test
    @Transactional
    void add() {
        accountDao.save(User.builder().username("cheburek").password("pass").build());
        Optional<User> results = accountDao.findUserByUsername("cheburek");
        assertTrue(results.isPresent());
    }

    @Test
    @Transactional
    void update() {
        Optional<Account> byId = accountDao.findById(3L);
        byId.ifPresent(account -> {
            account.setUsername("librarian");
            accountDao.update(account);
        });
        Optional<Account> updatedAccount = accountDao.findById(3L);
        updatedAccount.ifPresent(account -> assertEquals("librarian", account.getUsername()));
    }

    @Test
    void findUserById() {
        Optional<User> userById = accountDao.findUserById(1L);
        userById.ifPresent(user -> {
            assertEquals("user", user.getUsername());
            assertEquals("user", user.getRole());
        });
    }

    @Test
    void findAllDebtors() {
        List<User> allDebtors = accountDao.findAllDebtors();
        assertEquals(0, allDebtors.size());
    }
}