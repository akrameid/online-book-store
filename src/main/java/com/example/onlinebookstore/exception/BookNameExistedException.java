package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_NAME_EXISTED;

public class BookNameExistedException extends RuntimeException {
    public BookNameExistedException(final String bookName) {
        super(String.format(BOOK_NAME_EXISTED, bookName));
    }
}
