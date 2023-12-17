package io.datajek.moneytransferrest.exception;

//TODO: diferencias exception y runtime, que es handler etc
public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException(String message) {
        super(message);
    }
}
