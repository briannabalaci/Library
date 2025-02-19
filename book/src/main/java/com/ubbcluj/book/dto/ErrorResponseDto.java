package com.ubbcluj.book.dto;

public record ErrorResponseDto(String errorCode,
                               String errorName,
                               String errorMessage) {
}