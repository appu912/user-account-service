package com.progmatic.store.account.exception;

public class UserNotFoundException extends UserAPIException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
