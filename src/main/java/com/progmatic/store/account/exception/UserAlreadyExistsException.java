package com.progmatic.store.account.exception;

public class UserAlreadyExistsException extends UserAPIException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
