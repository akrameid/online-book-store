package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_REQUEST_NOT_CREATED;

public class BookRequestNotCreatedException extends RuntimeException {
    public BookRequestNotCreatedException(final Long bookId) {
        super(String.format(BOOK_REQUEST_NOT_CREATED, bookId));
    }
}
