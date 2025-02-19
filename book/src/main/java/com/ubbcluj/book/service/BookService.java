package com.ubbcluj.book.service;

import com.ubbcluj.book.dto.*;
import com.ubbcluj.book.exception.EntityNotFoundException;
import com.ubbcluj.book.exception.RequestNotValidException;
import com.ubbcluj.book.persistence.BookRepository;
import com.ubbcluj.book.persistence.UserRepository;
import com.ubbcluj.book.persistence.entity.BookEntity;
import com.ubbcluj.book.persistence.entity.UserEntity;
import com.ubbcluj.book.utils.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class BookService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final Converter converter;

    private final RestTemplate restTemplate = new RestTemplate();

    public BookService(UserRepository userRepository, BookRepository bookRepository, Converter converter) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.converter = converter;
    }

    public List<BookDto> getAllBooks() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        return bookEntities.stream().map(converter::convertToBookDto).toList();
    }


    public BookDto createBook(BookDto book) throws EntityNotFoundException {
        UserEntity user = userRepository.findByUsername(book.createdBy())
                .orElseThrow(EntityNotFoundException::new);
        BookEntity bookEntity = converter.convertToSaveBookEntity(book);
        bookEntity.setCreatedBy(user);
        bookEntity = bookRepository.save(bookEntity);
        if (bookEntity.getId() == null) {
            log.error("Book was not persisted to DB");

        }
        BookDto bookDto = converter.convertToBookDto(bookEntity);
        log.info("Book created successfully {}", bookDto);
        return bookDto;
    }

    public BookDto updateStatus(BookDto book) throws EntityNotFoundException, RequestNotValidException {
        if (book == null || book.id() == null) {
            throw new RequestNotValidException();
        }

        BookEntity bookEntity = bookRepository.findById(book.id())
                .orElseThrow(EntityNotFoundException::new);

        bookEntity.setStatus(book.status());
        bookEntity = bookRepository.save(bookEntity);

        BookDto bookDto = converter.convertToBookDto(bookEntity);
        log.info("Book updated successfully {}", bookDto);
        return bookDto;
    }

    public List<BookDto> getAllBooksAssignedTo(AssignUserDto assignUserDto) throws EntityNotFoundException {
        log.info("User to look for book assigned to {}", assignUserDto);
        UserEntity userEntity = userRepository.findByUsername(assignUserDto.username())
                .orElseThrow(EntityNotFoundException::new);
        List<BookEntity> bookEntities = bookRepository.findByAssignedTo(userEntity.getUsername());
        return bookEntities.stream().map(converter::convertToBookDto).toList();
    }


    public BookDto updateBook(BookDto book) throws EntityNotFoundException, RequestNotValidException {
        if (book == null || book.id() == null) {
            throw new RequestNotValidException();
        }

        BookEntity bookEntity = bookRepository.findById(book.id())
                .orElseThrow(EntityNotFoundException::new);

        bookEntity.setDescription(book.description());
        bookEntity.setTitle(book.title());
        bookEntity.setDueDate(book.dueDate());

        bookEntity = bookRepository.save(bookEntity);

        BookDto bookDto = converter.convertToBookDto(bookEntity);
        log.info("Book updated successfully {}", bookDto);
        return bookDto;
    }

    public BookDto getBookById(Long id) throws EntityNotFoundException {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        log.info("Book found {}", bookEntity);
        return converter.convertToBookDto(bookEntity);
    }

    public BookDto assignBook(Long bookId, AssignUserDto userDto) throws EntityNotFoundException {
        log.info("Book will be assigned to {}", userDto);
        UserEntity userEntity = userRepository.findByUsername(userDto.username())
                .orElseThrow(EntityNotFoundException::new);
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);

        bookEntity.setAssignedTo(userEntity);
        bookEntity = bookRepository.save(bookEntity);

        BookDto bookDto = converter.convertToBookDto(bookEntity);
        log.info("Book assigned successfully {} to user {}", bookDto, userEntity);
        return bookDto;
    }


    public void deleteBook(Long bookId) throws EntityNotFoundException {
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);
        bookRepository.delete(bookEntity);
    }

}
