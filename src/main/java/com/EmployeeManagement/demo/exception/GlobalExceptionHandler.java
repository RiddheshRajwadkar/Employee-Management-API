package com.EmployeeManagement.demo.exception;

import com.EmployeeManagement.demo.dtos.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(ResourceNotFoundException ex) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(timestamp, 404, "Resource Not Found Exception");
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateResource(DuplicateResourceException ex) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(timestamp, 409, "Duplicate Resource Exception");
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials(BadCredentialsException ex) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(timestamp, 401, "Unauthorized");
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDenied(AccessDeniedException ex) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(timestamp, 403, "Forbidden");
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(timestamp, 400, "Bad Request");
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(timestamp, 500, "Internal Server Error");
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}