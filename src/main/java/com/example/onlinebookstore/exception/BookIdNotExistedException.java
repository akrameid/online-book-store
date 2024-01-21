package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_ID_NOT_EXISTED;

public class BookIdNotExistedException extends RuntimeException {
    public BookIdNotExistedException(final Long bookId) {
        super(String.format(BOOK_ID_NOT_EXISTED, bookId));
    }
}
