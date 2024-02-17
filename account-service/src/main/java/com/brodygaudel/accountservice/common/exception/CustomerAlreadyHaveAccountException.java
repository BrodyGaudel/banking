package com.brodygaudel.accountservice.common.exception;

public class CustomerAlreadyHaveAccountException extends RuntimeException{
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CustomerAlreadyHaveAccountException(String message) {
        super(message);
    }
}