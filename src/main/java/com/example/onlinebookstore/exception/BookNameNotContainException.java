package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_NAME_NOT_CONTAIN;

public class BookNameNotContainException extends RuntimeException {
    public BookNameNotContainException(final String bookName) {
        super(String.format(BOOK_NAME_NOT_CONTAIN, bookName));
    }
}
