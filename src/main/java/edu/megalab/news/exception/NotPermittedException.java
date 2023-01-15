package edu.megalab.news.exception;

public class NotPermittedException extends RuntimeException {
    public NotPermittedException() {
    }

    public NotPermittedException(String message) {
        super(message);
    }
}
