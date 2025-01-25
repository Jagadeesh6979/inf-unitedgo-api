package com.unitedGo.util;

import com.unitedGo.model.ErrorInfo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler{

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> pathExceptionHandler(ConstraintViolationException exception) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        String errorMsg = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        errorInfo.setErrorMessage(errorMsg);
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorInfo> httpExceptionHandler(HttpClientErrorException exception) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorCode(exception.getStatusCode().value());
        errorInfo.setErrorMessage(exception.getMessage());
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(RuntimeException exception) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setErrorMessage(exception.getMessage());
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(MethodArgumentNotValidException exception) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setTimestamp(LocalDateTime.now());

        String errorMsg = exception.getBindingResult().getAllErrors().stream().map(x -> x.getDefaultMessage())
                .collect(Collectors.joining(", "));
        errorInfo.setErrorMessage(errorMsg);

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }


}
