package com.gabrielgua.realworld.api.exception;

import com.gabrielgua.realworld.domain.exception.AlreadyTakenException;
import com.gabrielgua.realworld.domain.exception.ArticleAlreadyRegisteredException;
import com.gabrielgua.realworld.domain.exception.BusinessException;
import com.gabrielgua.realworld.domain.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


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

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleGeneric(BusinessException ex, WebRequest request) {
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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        var status = HttpStatus.FORBIDDEN;
        var message = "Missing credentials or you don't have access to this resource";

        var error = createErrorBuilder(status, message).build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ArticleAlreadyRegisteredException.class)
    public ResponseEntity<?> handleArticleAlreadyRegistered(ArticleAlreadyRegisteredException ex, WebRequest request) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        var message = ex.getMessage();

        var error = createErrorBuilder(status, message).build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        var message = ex.getMessage();

        var error = createErrorBuilder(status, message).build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }























}