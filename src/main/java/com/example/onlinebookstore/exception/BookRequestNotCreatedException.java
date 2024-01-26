package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_REQUEST_NOT_CREATED;
import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_REQUEST_WITH_BOOK_USER_NOT_CREATED;

public class BookRequestNotCreatedException extends RuntimeException {
    public BookRequestNotCreatedException(final Long bookRequestId) {
        super(String.format(BOOK_REQUEST_NOT_CREATED, bookRequestId));
    }

    public BookRequestNotCreatedException(final Long bookId, final Long userId) {
        super(String.format(BOOK_REQUEST_WITH_BOOK_USER_NOT_CREATED, bookId, userId));
    }
}
