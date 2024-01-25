package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_NOT_AVAILABLE;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(final String bookName) {
        super(String.format(BOOK_NOT_AVAILABLE, bookName));
    }
}
