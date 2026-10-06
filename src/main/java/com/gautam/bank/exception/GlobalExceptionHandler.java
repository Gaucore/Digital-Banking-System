// package com.gautam.bank.exception;

// import java.util.List;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;

// import com.gautam.bank.util.ApiErrorResponse;

// import jakarta.servlet.http.HttpServletRequest;

// @RestControllerAdvice
// public class GlobalExceptionHandler {

//     @ExceptionHandler(ResourceNotFoundException.class)
//     public ResponseEntity<ApiErrorResponse> handleNotFound(
//             ResourceNotFoundException ex,
//             HttpServletRequest request) {

//         ApiErrorResponse response = ApiErrorResponse.builder()
//                 .success(false)
//                 .status(HttpStatus.NOT_FOUND.value())
//                 .message(ex.getMessage())
//                 .errors(List.of(ex.getMessage()))
//                 .path(request.getRequestURI())
//                 .build();

//         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//     }

//     @ExceptionHandler(DuplicateResourceException.class)
//     public ResponseEntity<ApiErrorResponse> handleDuplicate(
//             DuplicateResourceException ex,
//             HttpServletRequest request) {

//         ApiErrorResponse response = ApiErrorResponse.builder()
//                 .success(false)
//                 .status(HttpStatus.CONFLICT.value())
//                 .message(ex.getMessage())
//                 .errors(List.of(ex.getMessage()))
//                 .path(request.getRequestURI())
//                 .build();

//         return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
//     }

// }


package com.gautam.bank.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gautam.bank.util.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===========================
    // Validation Exception
    // ===========================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiErrorResponse response = ApiErrorResponse.builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation Failed")
                .errors(errors)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    // ===========================
    // Resource Not Found
    // ===========================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .success(false)
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // ===========================
    // Duplicate Resource
    // ===========================
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicate(
            DuplicateResourceException ex,
            HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .success(false)
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // ===========================
    // Generic Exception
    // ===========================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request) {

        ApiErrorResponse response = ApiErrorResponse.builder()
                .success(false)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .errors(List.of(ex.getMessage()))
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}