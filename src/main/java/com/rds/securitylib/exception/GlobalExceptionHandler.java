package com.rds.securitylib.exception;

import com.rds.securitylib.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest webRequest) {
        log.error("Something went wrong, api path: {}", webRequest.getDescription(false), e);
        return new ResponseEntity<>(
                new ErrorResponse(
                        webRequest.getDescription(false),
                        "Something went wrong",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        LocalDateTime.now()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest webRequest) {
        log.error("Validation error, api path: {}", webRequest.getDescription(false), e);
        return new ResponseEntity<>(
                new ErrorResponse(
                        webRequest.getDescription(false),
                        "Validation error",
                        HttpStatus.BAD_REQUEST,
                        LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e, WebRequest webRequest) {
        log.error("Authorization denied, api path: {}", webRequest.getDescription(false), e);
        return new ResponseEntity<>(
                new ErrorResponse(
                        webRequest.getDescription(false),
                        "Authorization denied",
                        HttpStatus.FORBIDDEN,
                        LocalDateTime.now()
                ),
                HttpStatus.FORBIDDEN
        );
    }
}
