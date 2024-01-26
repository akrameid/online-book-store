package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_REQUEST_ALREADY_APPROVED;

public class BookRequestAlreadyApprovedException extends RuntimeException {
    public BookRequestAlreadyApprovedException(final Long bookRequestId) {
        super(String.format(BOOK_REQUEST_ALREADY_APPROVED, bookRequestId));
    }
}
