package com.perso.bio.controller.advice;

import com.perso.bio.constants.MessageConstants;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstants.INTERNAL_ERROR + e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstants.INVALID_FORMAT);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<String> handleDuplicateKeyException(DuplicateKeyException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MessageConstants.MAIL_ALREADY_EXIST + e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageConstants.TOKEN_UNKNOWN + e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleNoSuchFileException(FileNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageConstants.FILE_NOT_FOUND + e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<String> handleNoSuchURLException(MalformedURLException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageConstants.MALFORMED_URL + e.getMessage());
    }



}
