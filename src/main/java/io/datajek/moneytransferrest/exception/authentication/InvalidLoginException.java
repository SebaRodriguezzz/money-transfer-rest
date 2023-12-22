package io.datajek.moneytransferrest.exception.authentication;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException(String message) {
        super(message);
    }
}
