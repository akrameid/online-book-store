package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_RETURNED;

public class BookReturnedException extends RuntimeException {
    public BookReturnedException(final String bookName) {
        super(String.format(BOOK_RETURNED, bookName));
    }
}
