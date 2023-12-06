package com.perso.bio.controller.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.perso.bio.constants.MessageConstants;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.directory.InvalidAttributeValueException;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageConstants.INTERNAL_ERROR + e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MessageConstants.FORBIDDEN_LOGIN + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstants.INVALID_FORMAT + e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidAttributeValueException.class)
    public ResponseEntity<String> handleInvalidAttributeValueException(InvalidAttributeValueException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstants.INVALID_ATTRIBUTE + e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(MessageConstants.FORBIDDEN_LOGIN + e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(MessageConstants.CONNECTION_EXPIRED + e.getMessage());
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleJsonProcessingException(JsonProcessingException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstants.JSON_PROCESSING_ERROR + e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<String> handleMessagingException(MessagingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageConstants.JSON_PROCESSING_ERROR + e.getMessage());
    }


}
