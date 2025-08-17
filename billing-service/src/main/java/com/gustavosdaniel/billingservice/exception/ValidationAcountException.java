package com.gustavosdaniel.billingservice.exception;

public class ValidationAcountException extends RuntimeException {

    public ValidationAcountException() {
    }

    public ValidationAcountException(String message) {
        super(message);
    }

    public ValidationAcountException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationAcountException(Throwable cause) {
        super(cause);
    }

    public ValidationAcountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
