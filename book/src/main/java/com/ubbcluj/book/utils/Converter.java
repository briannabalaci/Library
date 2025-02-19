package com.ubbcluj.book.utils;

import com.ubbcluj.book.dto.BookDto;
import com.ubbcluj.book.dto.UserDto;
import com.ubbcluj.book.persistence.entity.BookEntity;
import com.ubbcluj.book.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public UserDto convertToUserDto(UserEntity userEntity) {
        return new UserDto(userEntity.getUsername(), userEntity.getEmail(), userEntity.getDateOfBirth());
    }

    public BookEntity convertToSaveBookEntity(BookDto bookDto) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setDescription(bookDto.description());
        bookEntity.setTitle(bookDto.title());
        bookEntity.setStatus(bookDto.status());
        bookEntity.setDueDate(bookDto.dueDate());
        return bookEntity;
    }

    public BookDto convertToBookDto(BookEntity bookEntity) {
        return new BookDto(bookEntity.getId(), bookEntity.getTitle(), bookEntity.getDescription(), bookEntity.getStatus(),
                bookEntity.getDueDate(), bookEntity.getCreatedBy().getUsername(), bookEntity.getAssignedTo() != null ? bookEntity.getAssignedTo().getUsername() : "Unassigned");
    }
}
