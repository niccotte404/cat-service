package com.catservice.ownermicroservice.exceptions;

public class UserEntityDoesNotExists extends RuntimeException {
    public UserEntityDoesNotExists(String message) {
        super(message);
    }

    public UserEntityDoesNotExists(String message, Throwable cause) {
        super(message, cause);
    }
}
