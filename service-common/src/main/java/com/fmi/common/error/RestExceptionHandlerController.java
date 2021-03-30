package com.fmi.common.error;

import com.fmi.common.exception.BadRequestException;
import com.fmi.common.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleException(NotFoundException ex) {
        String error = "Not Found";
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, ex));
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleException(BadRequestException ex) {
        String error = "Bad Request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseEntity<Object> handleException(BadCredentialsException ex) {
        String error = "Unauthorized Request";
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, error, ex));
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public final ResponseEntity<Object> handleException(HttpClientErrorException.Forbidden ex) {
        String error = "Forbidden";
        return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, error, ex));
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiSubError> listSubError = new ArrayList<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(objectError -> listSubError.add(new ApiValidationError(objectError.getObjectName(), objectError.getDefaultMessage())));
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex, listSubError));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}