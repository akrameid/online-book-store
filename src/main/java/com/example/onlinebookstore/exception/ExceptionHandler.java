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
    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userAlreadyRegisteredException(final UserAlreadyRegisteredException ex) {
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
    @org.springframework.web.bind.annotation.ExceptionHandler(BookRequestNotApprovedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookRequestNotApprovedException(final BookRequestNotApprovedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BookReturnedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookReturnedException(final BookReturnedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(UserIdNotExistedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userIdNotExistedException(final UserIdNotExistedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BookRequestNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookRequestNotCreatedException(final BookRequestNotCreatedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BookRequestAlreadyRejectedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookRequestAlreadyRejectedException(final BookRequestAlreadyRejectedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BookRequestAlreadyApprovedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookRequestAlreadyApprovedException(final BookRequestAlreadyApprovedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BookNameExistedInOtherBookException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookNameExistedInOtherBookException(final BookNameExistedInOtherBookException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BookCopiesExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookCopiesExceededException(final BookCopiesExceededException ex) {
        return ex.getMessage();
    }


}
