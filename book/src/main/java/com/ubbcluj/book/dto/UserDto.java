package com.ubbcluj.book.dto;

import java.time.LocalDate;

public record UserDto(String username, String email, LocalDate dateOfBirth) {
}
