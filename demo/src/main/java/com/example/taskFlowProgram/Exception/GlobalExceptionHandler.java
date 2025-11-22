package com.example.taskFlowProgram.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ApiError buildError(HttpStatus status, String message, HttpServletRequest req) {
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(message)
                .path(req.getRequestURI())
                .build();
    }

    // 404 - Resource not found
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiError> handleTaskNotFound(TaskNotFoundException ex, HttpServletRequest req) {
        return ResponseEntity  //All ready defined by java ResponseEntity class
                .status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage(), req));
    }

    // 400 - Validation or bad request errors
    @ExceptionHandler(InvalidTaskException.class)
    public ResponseEntity<ApiError> handleInvalidTask(InvalidTaskException ex, HttpServletRequest req) {
        return ResponseEntity
                .badRequest()
                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), req));
    }

    // 400 - For RuntimeException (your business rules)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntime(RuntimeException ex, HttpServletRequest req) {
        return ResponseEntity
                .badRequest()
                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), req));
    }

    // 500 - Any unknown/unhandled error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", req));
    }
}
