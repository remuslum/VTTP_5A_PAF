package sg.nus.edu.iss.vttp_5a_day27_workshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.InvalidGameIDException;
import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.InvalidRatingException;
import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.InvalidReviewIDException;
import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.MissingValueException;
import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.TooManyArgumentsException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({InvalidGameIDException.class, InvalidRatingException.class, MissingValueException.class,
    InvalidReviewIDException.class, TooManyArgumentsException.class})
    public ResponseEntity<String> handleExceptions(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
