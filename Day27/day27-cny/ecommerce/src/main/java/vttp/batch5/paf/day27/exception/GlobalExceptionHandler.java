package vttp.batch5.paf.day27.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vttp.batch5.paf.day27.models.exceptions.InvalidValueException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({InvalidValueException.class})
    public ResponseEntity<String> handleInvalidValue(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
