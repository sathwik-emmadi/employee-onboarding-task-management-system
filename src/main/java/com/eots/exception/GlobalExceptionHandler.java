package com.eots.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.eots.exception.InvalidCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eots.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<ApiResponse<Void>> handleEmailAlreadyExistsException(
                        EmailAlreadyExistsException exception) {

                ApiResponse<Void> response = new ApiResponse<>();

                response.setSuccess(false);
                response.setMessage(exception.getMessage());
                response.setData(null);

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(response);
        }

        @ExceptionHandler(InvalidCredentialsException.class)
        public ResponseEntity<ApiResponse<Void>> handleInvalidCredentialsException(
                        InvalidCredentialsException exception) {

                ApiResponse<Void> response = new ApiResponse<>();

                response.setSuccess(false);
                response.setMessage(exception.getMessage());
                response.setData(null);

                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(response);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
                        MethodArgumentNotValidException exception) {

                Map<String, String> errors = new HashMap<>();

                for (FieldError error : exception.getBindingResult().getFieldErrors()) {
                        errors.put(error.getField(), error.getDefaultMessage());
                }

                ApiResponse<Map<String, String>> response = new ApiResponse<>();

                response.setSuccess(false);
                response.setMessage("Validation Failed");
                response.setData(errors);

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(response);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<Void>> handleException(
                        Exception exception) {

                ApiResponse<Void> response = new ApiResponse<>();

                response.setSuccess(false);
                response.setMessage("Something went wrong.");
                response.setData(null);

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(response);
        }

        @ExceptionHandler(EmployeeNotFoundException.class)
        public ResponseEntity<ApiResponse> handleEmployeeNotFound(
                        EmployeeNotFoundException ex) {

                ApiResponse response = new ApiResponse(
                                false,
                                ex.getMessage());

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(response);
        }

        @ExceptionHandler(TaskNotFoundException.class)
        public ResponseEntity<ApiResponse<Object>> handleTaskNotFound(
                        TaskNotFoundException ex) {

                ApiResponse<Object> response = new ApiResponse<>();

                response.setSuccess(false);
                response.setMessage(ex.getMessage());

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(response);
        }

        @ExceptionHandler(UnauthorizedTaskAccessException.class)
        public ResponseEntity<ApiResponse<Object>> handleUnauthorizedTaskAccess(
                        UnauthorizedTaskAccessException ex) {

                ApiResponse<Object> response = new ApiResponse<>();

                response.setSuccess(false);
                response.setMessage(ex.getMessage());

                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .body(response);
        }
}