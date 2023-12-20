package io.datajek.moneytransferrest.exception;

public class SameAccountTransactionException extends RuntimeException {
    public SameAccountTransactionException(String message) {
        super(message);
    }
}
