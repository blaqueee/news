package edu.megalab.news.exception;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException() {
    }

    public UsernameExistsException(String message) {
        super(message);
    }
}
