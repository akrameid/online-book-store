package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_NAME_EXISTED_IN_ANOTHER_BOOK;

public class BookNameExistedInOtherBookException extends RuntimeException {
    public BookNameExistedInOtherBookException(final String bookName) {
        super(String.format(BOOK_NAME_EXISTED_IN_ANOTHER_BOOK, bookName));
    }
}
