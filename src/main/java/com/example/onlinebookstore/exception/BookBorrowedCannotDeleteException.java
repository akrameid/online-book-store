package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.BOOK_ID_BORROWED_DELETE_NOT_ALLOWED;

public class BookBorrowedCannotDeleteException extends RuntimeException {
    public BookBorrowedCannotDeleteException(final Long bookId) {
        super(String.format(BOOK_ID_BORROWED_DELETE_NOT_ALLOWED, bookId));
    }
}
