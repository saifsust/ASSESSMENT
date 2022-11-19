package com.customerprocessor.exception;

public final class ApplicationException extends RuntimeException {
    public ApplicationException(Throwable throwable) {
        super(throwable);
    }

    public ApplicationException(String message) {
        super(message);
    }
}
