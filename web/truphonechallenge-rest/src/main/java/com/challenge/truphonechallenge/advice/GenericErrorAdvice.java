package com.challenge.truphonechallenge.advice;

import com.challenge.truphonechallenge.exceptions.GenericErrorException;
import com.challenge.truphonechallenge.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GenericErrorAdvice {

    private final Logger logger = LoggerFactory.getLogger(GenericErrorException.class);

    @ResponseBody
    @ExceptionHandler(GenericErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String genericException(GenericErrorException ex) {
        logger.error(String.format("...................> Error -> %s", ex.getMessage()));
        return null;
    }
}