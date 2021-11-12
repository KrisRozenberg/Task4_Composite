package org.rozenberg.task4.exception;

public class CustomException extends Exception{
    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Exception cause) {
        super(message, cause);
    }

    public CustomException(Exception cause) {
        super(cause);
    }
}
