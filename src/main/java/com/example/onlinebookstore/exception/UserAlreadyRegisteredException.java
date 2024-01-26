package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.USER_ALREADY_REGISTERED;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(final String userName) {
        super(String.format(USER_ALREADY_REGISTERED, userName));
    }
}
