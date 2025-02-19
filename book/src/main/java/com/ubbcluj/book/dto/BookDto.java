package com.ubbcluj.book.dto;

import com.ubbcluj.book.persistence.entity.enums.BookStatus;

import java.time.LocalDate;

public record BookDto(Long id, String title, String description, BookStatus status, LocalDate dueDate,
                      String createdBy, String assignedTo) {

}
