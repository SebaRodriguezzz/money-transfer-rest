package io.datajek.moneytransferrest.exception.transaction;

public class SameAccountTransactionException extends RuntimeException {
    public SameAccountTransactionException(String message) {
        super(message);
    }
}
