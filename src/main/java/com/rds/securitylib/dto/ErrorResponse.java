package com.rds.securitylib.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse (
    String apiPath,
    String errorMessage,
    HttpStatus httpStatus,
    LocalDateTime timestamp
){ }