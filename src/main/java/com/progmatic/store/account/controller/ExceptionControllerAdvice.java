package com.progmatic.store.account.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.progmatic.store.account.exception.UserAlreadyExistsException;
import com.progmatic.store.account.exception.UserNotFoundException;
import com.progmatic.store.account.response.ErrorResponse;
import com.progmatic.store.account.response.ValidationErrorResponse;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
        response.setMessage(exception.getMessage());
        response.setTimestamp(LocalDateTime.now());
        response.setPath(request.getRequestURI());
        log.warn("User not found: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.setStatusCode(HttpStatus.CONFLICT.value());
        response.setStatus(HttpStatus.CONFLICT.getReasonPhrase());
        response.setMessage(exception.getMessage());
        response.setTimestamp(LocalDateTime.now());
        response.setPath(request.getRequestURI());
        log.warn("User already exists: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> violatedConstraints = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach((error) -> violatedConstraints.put(error.getField(), error.getDefaultMessage()));
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setMessage("User validations failed.");
        response.setViolatedConstraints(violatedConstraints);
        response.setTimestamp(LocalDateTime.now());
        response.setPath(request.getRequestURI());
        log.warn("User validations failed: {}", exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {
        Map<String, String> violatedConstraints = new HashMap<>();
        exception.getConstraintViolations().forEach((error) -> violatedConstraints.put(error.getPropertyPath().toString(), error.getMessage()));
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setViolatedConstraints(violatedConstraints);
        response.setMessage("User validations failed.");
        response.setTimestamp(LocalDateTime.now());
        response.setPath(request.getRequestURI());
        log.warn("User validations failed: {}", exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.setMessage(exception.getMessage());
        response.setTimestamp(LocalDateTime.now());
        response.setPath(request.getRequestURI());
        log.warn("Internal server error: {}", exception.getMessage(), exception);
        return ResponseEntity.internalServerError().body(response);
    }
}
