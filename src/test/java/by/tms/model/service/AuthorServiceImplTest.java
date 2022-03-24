package by.tms.model.service;

import by.tms.model.config.DatabaseConfigTest;
import by.tms.model.dto.AuthorDto;
import by.tms.util.TestDataImporter;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.tms.util.TestDataImporter.LIMIT_10;
import static by.tms.util.TestDataImporter.OFFSET_0;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DatabaseConfigTest.class)
@Transactional
class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void initDb() {
        TestDataImporter.importTestData(sessionFactory);
    }

    @AfterEach
    public void flush() {
        sessionFactory.close();
    }

    @Test
    void findAllAuthor() {
        List<AuthorDto> allAuthor = authorService.findAllAuthor(LIMIT_10,OFFSET_0);
        assertEquals(3, allAuthor.size());
    }

    @Test
    void addNewAuthor() {
        authorService.addNewAuthor(AuthorDto.builder().fullName("Анджей Сапковский").build());
        Optional<AuthorDto> authorDto = authorService.findAuthorByFullName("Анджей Сапковский");
        assertTrue(authorDto.isPresent());
    }

    @Test
    void findAuthorByFullName() {
        Optional<AuthorDto> byFullName = authorService.findAuthorByFullName("Борис Акунин");
        assertTrue(byFullName.isPresent());
    }

    @Test
    void updateAuthor() {
        Optional<AuthorDto> optionalAuthor = authorService.findAuthorById(2L);
        optionalAuthor.ifPresent(author -> {
            author.setFullName("Виктор Ципеленен");
            authorService.updateAuthor(author);
        });
        Optional<AuthorDto> authorById = authorService.findAuthorById(2L);
        authorById.ifPresent(authorDto -> assertEquals("Виктор Ципеленен", authorDto.getFullName()));
    }

    @Test
    void deleteAuthor() {
        Optional<AuthorDto> optionalAuthor = authorService.findAuthorById(1L);
        optionalAuthor.ifPresent(author -> authorService.deleteAuthor(author));
        Optional<AuthorDto> authorById = authorService.findAuthorById(1L);
        assertFalse(authorById.isPresent());
    }

    @Test
    void findAuthorById() {
        Optional<AuthorDto> authorById = authorService.findAuthorById(3L);
        authorById.ifPresent(authorDto -> assertEquals("Чарльз Буковски", authorDto.getFullName()));
    }
}
