package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception.AccountInactiveException;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception.AccountNotFoundException;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception.ErrorMessage;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception.InsufficientBalanceException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({AccountNotFoundException.class, AccountInactiveException.class, InsufficientBalanceException.class})
    public ResponseEntity<ErrorMessage> handleAccuountNotFoundException(RuntimeException ex, HttpServletRequest request, 
    HttpServletResponse response){
        ErrorMessage message = new ErrorMessage(response.getStatus(), ex.getMessage(), 
        LocalDateTime.now(), request.getRequestURI());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
