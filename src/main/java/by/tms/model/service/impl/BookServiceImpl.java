package by.tms.model.service.impl;

import by.tms.model.dao.BookDao;
import by.tms.model.dto.BookDto;
import by.tms.model.entity.Book;
import by.tms.model.mapper.impl.BookMapper;
import by.tms.model.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final BookMapper bookMapper = BookMapper.getInstance();

    @Override
    public Optional<BookDto> findBookById(Long id) {
        Optional<Book> optionalBook = bookDao.findById(id);
        return Optional.ofNullable(bookMapper.mapToDto(optionalBook.orElse(null)));
    }

    @Override
    public List<BookDto> findBookByName(String bookName) {
        List<Book> books = bookDao.findByName(bookName);
        return bookMapper.mapToListDto(books);
    }

    @Override
    public List<BookDto> findAllBook() {
        List<Book> books = bookDao.findAll();
        return bookMapper.mapToListDto(books);
    }

    @Override
    @Transactional
    public Long addNewBook(BookDto bookDto) {
        return bookDao.save(bookMapper.mapToEntity(bookDto));
    }

    @Override
    @Transactional
    public void updateBook(BookDto bookDto) {
        bookDao.update(bookMapper.mapToEntity(bookDto));
    }

    @Override
    @Transactional
    public void deleteBook(BookDto bookDto) {
        bookDao.delete(bookMapper.mapToEntity(bookDto));
    }

    @Override
    public boolean isBookExist(Long id) {
        return bookDao.isExist(id);
    }
}