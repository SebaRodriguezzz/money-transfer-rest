package io.datajek.moneytransferrest;

public class BankUserNotFoundException extends RuntimeException{


    public BankUserNotFoundException() {
        super();

    }

    public BankUserNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }

    public BankUserNotFoundException(String message, Throwable cause) {
        super(message, cause);

    }

    public BankUserNotFoundException(String message) {
        super(message);

    }

    public BankUserNotFoundException(Throwable cause) {
        super(cause);
    }
}