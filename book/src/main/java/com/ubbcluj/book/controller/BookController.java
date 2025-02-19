package com.ubbcluj.book.controller;

import com.ubbcluj.book.config.LogToKafka;
import com.ubbcluj.book.dto.AssignUserDto;
import com.ubbcluj.book.dto.BookDto;
import com.ubbcluj.book.exception.EntityNotFoundException;
import com.ubbcluj.book.exception.RequestNotValidException;
import com.ubbcluj.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/all",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(this.bookService.getAllBooks());
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/save", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto bookDto) throws EntityNotFoundException {
        return ResponseEntity.ok(this.bookService.createBook(bookDto));
    }


    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value="/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(this.bookService.getBookById(id));
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/update", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) throws EntityNotFoundException, RequestNotValidException {
        return ResponseEntity.ok(this.bookService.updateBook(bookDto));
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/update-status", produces = APPLICATION_JSON_VALUE)
     public ResponseEntity<BookDto> updateBookStatus(@RequestBody BookDto bookDto) throws EntityNotFoundException, RequestNotValidException {
        return ResponseEntity.ok(this.bookService.updateStatus(bookDto));
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable("id") Long id) throws EntityNotFoundException {
        this.bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/all-assigned-to")
    public ResponseEntity<List<BookDto>> getAllBooksAssignedTo(@RequestBody AssignUserDto assignUserDto) throws EntityNotFoundException {
        return ResponseEntity.ok(this.bookService.getAllBooksAssignedTo(assignUserDto));
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/assign/{bookId}")
    public ResponseEntity<Object> assignBook(@PathVariable("bookId") Long bookId, @RequestBody AssignUserDto userDto) throws EntityNotFoundException {
        this.bookService.assignBook(bookId, userDto);
        return ResponseEntity.ok().build();
    }
}
