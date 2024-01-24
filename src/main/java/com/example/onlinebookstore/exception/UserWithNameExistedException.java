package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.USER_NAME_EXISTED;

public class UserWithNameExistedException extends RuntimeException {
    public UserWithNameExistedException(final String userName) {
        super(String.format(USER_NAME_EXISTED, userName));
    }
}
