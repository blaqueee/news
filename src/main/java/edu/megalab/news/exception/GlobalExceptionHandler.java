package edu.megalab.news.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String constraintViolationExceptionHandler(BindException e) {
        return Objects.requireNonNull(e.getBindingResult()
                .getFieldError())
                .getField()
                + " "
                + e.getBindingResult()
                .getFieldError()
                .getDefaultMessage();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notFoundExceptionHandler(NotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UsernameExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String usernameExistsExceptionHandler(UsernameExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(FileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String fileExceptionHandler(FileException e) {
        return e.getMessage();
    }

    @ExceptionHandler(FileWriteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String fileWriteExceptionHandler(FileWriteException e) {
        return e.getMessage();
    }

    @ExceptionHandler(GCPFileUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String GCPFileUploadExceptionHandler(GCPFileUploadException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notPermittedExceptionHandler(NotPermittedException e) {
        return e.getMessage();
    }
}
