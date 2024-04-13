package com.catservice.ownermicroservice.exceptions;

public class OwnerDoesNotExistsException extends RuntimeException {
    public OwnerDoesNotExistsException(String message) {
        super(message);
    }

    public OwnerDoesNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
