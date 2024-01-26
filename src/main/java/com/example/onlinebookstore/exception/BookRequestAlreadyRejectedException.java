package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_REQUEST_ALREADY_REJECTED;

public class BookRequestAlreadyRejectedException extends RuntimeException {
    public BookRequestAlreadyRejectedException(final Long bookRequestId) {
        super(String.format(BOOK_REQUEST_ALREADY_REJECTED, bookRequestId));
    }
}
