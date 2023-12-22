package io.datajek.moneytransferrest.exception.transaction;

public class TransactionFailedException extends RuntimeException {
    public TransactionFailedException(String message) {
        super(message);
    }
}
