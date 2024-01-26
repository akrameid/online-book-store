package com.example.onlinebookstore.exception;

import static com.example.onlinebookstore.constant.ErrorMessages.NEW_BOOK_COPIES_EXCCEDED;

public class BookCopiesExceededException extends RuntimeException {
    public BookCopiesExceededException(final String bookName, final int copies, final int maxCopies) {
        super(String.format(NEW_BOOK_COPIES_EXCCEDED, bookName, copies, maxCopies));
    }
}
