package com.gabrielgua.realworld.api.exception;

import com.gabrielgua.realworld.domain.exception.AlreadyTakenException;
import com.gabrielgua.realworld.domain.exception.GenericException;
import com.gabrielgua.realworld.domain.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final String GENERIC_ERROR_MESSAGE = "Oops! Something went wrong.";

    private Error.ErrorBuilder createErrorBuilder(HttpStatus status, String message) {
        return Error.builder()
                .status(status.value())
                .message(message);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if (body == null || body instanceof String) {
            body = createErrorBuilder(HttpStatus.valueOf(statusCode.value()), GENERIC_ERROR_MESSAGE)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var error = createErrorBuilder(status, ex.getMessage())
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFound(UsernameNotFoundException ex, WebRequest request) {
        var status = HttpStatus.UNAUTHORIZED;
        var error = createErrorBuilder(status, "missing authorization credentials, token invalid")
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> handleGeneric(GenericException ex, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var message = ex.getMessage();
        var error = createErrorBuilder(status, ex.getMessage()).build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(AlreadyTakenException.class)
    public ResponseEntity<?> handleAlreadyTaken(AlreadyTakenException ex, WebRequest request) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        var message = ex.getMessage();

        var error = createErrorBuilder(status, message).build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }























}