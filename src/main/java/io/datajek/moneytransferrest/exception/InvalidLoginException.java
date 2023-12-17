package io.datajek.moneytransferrest.exception;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException(String message) {
        super(message);
    }
}
