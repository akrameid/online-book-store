package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_REQUEST_NOT_APPROVED;

public class BookRequestNotApprovedException extends RuntimeException {
    public BookRequestNotApprovedException(final String bookName) {
        super(String.format(BOOK_REQUEST_NOT_APPROVED, bookName));
    }
}
