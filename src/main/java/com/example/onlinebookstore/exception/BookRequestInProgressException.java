package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_REQUEST_IN_PROGRESS;

public class BookRequestInProgressException extends RuntimeException {
    public BookRequestInProgressException(final String bookName) {
        super(String.format(BOOK_REQUEST_IN_PROGRESS, bookName));
    }
}
