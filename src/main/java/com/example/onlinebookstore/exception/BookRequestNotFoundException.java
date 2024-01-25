package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_REQUEST_NOT_FOUND;

public class BookRequestNotFoundException extends RuntimeException {
    public BookRequestNotFoundException(final String bookName) {
        super(String.format(BOOK_REQUEST_NOT_FOUND, bookName));
    }
}
