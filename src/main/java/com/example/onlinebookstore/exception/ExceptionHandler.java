package com.example.onlinebookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BookNameExistedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookNameExistedException(final BookNameExistedException ex) {
        return ex.getMessage();
    }


    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BookIdNotExistedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookIdNotExistedException(final BookIdNotExistedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BookNameNotContainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookNameNotContainException(final BookNameNotContainException ex) {
        return ex.getMessage();
    }


    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(UserWithNameExistedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userWithNameExistedException(final UserWithNameExistedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BookNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookNotAvailableException(final BookNotAvailableException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BookRequestInProgressException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookRequestInProgressException(final BookRequestInProgressException ex) {
        return ex.getMessage();
    }


    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BookRequestNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookRequestNotFoundException(final BookRequestNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BookReturnedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookReturnedException(final BookReturnedException ex) {
        return ex.getMessage();
    }


}
