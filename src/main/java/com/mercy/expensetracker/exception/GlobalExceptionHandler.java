package com.mercy.expensetracker.exception;

import com.mercy.expensetracker.model.ErrorObject;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<ErrorObject> handleNotFound(ResourceException expenseException, WebRequest request){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setTimestamp(new Date());
        errorObject.setStatus(HttpStatus.NOT_FOUND.value());
        errorObject.setError(HttpStatus.NOT_FOUND.name());
        errorObject.setMessage(expenseException.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObject> handleMethodArgumentTypeMismatch(ResourceException expenseException, WebRequest request){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(expenseException.getMessage());
        errorObject.setError(HttpStatus.NOT_FOUND.name());
        errorObject.setTimestamp(new Date());
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handleGeneralException(Exception e, WebRequest request){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(e.getMessage());
        errorObject.setError(HttpStatus.INTERNAL_SERVER_ERROR.name());
        errorObject.setTimestamp(new Date());
        return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                //.map(x -> x.getDefaultMessage())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("message", errors);

        body.put("error", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
