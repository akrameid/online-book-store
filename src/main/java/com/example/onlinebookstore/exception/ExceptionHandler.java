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
}
