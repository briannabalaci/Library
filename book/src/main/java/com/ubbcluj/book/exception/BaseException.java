package com.ubbcluj.book.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class BaseException extends Exception{
    private final String errorCode;
    private final String errorName;
    private final String errorMessage;
}
