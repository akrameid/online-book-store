package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.USER_ID_NOT_EXISTED;

public class UserIdNotExistedException extends RuntimeException {
    public UserIdNotExistedException(final Long userId) {
        super(String.format(USER_ID_NOT_EXISTED, userId));
    }
}
