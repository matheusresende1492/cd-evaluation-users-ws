package com.cd.evaluation.users.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalException extends RuntimeException {

    public static final String INTERNAL_SERVER_ERROR = "http.error.500.message";
    public static final String BAD_REQUEST_ERROR = "http.error.400.message";
    public static final String NOT_FOUND_EXCEPTION = "http.error.404.message";
    public static final String DELETE_CONSTRAINT_EXCEPTION = "error.while.deleting.resource";

    private final HttpStatus httpStatus;

    public InternalException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
