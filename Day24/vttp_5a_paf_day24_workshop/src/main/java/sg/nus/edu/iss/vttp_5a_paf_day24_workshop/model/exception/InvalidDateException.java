package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateException extends RuntimeException{

    public InvalidDateException(LocalDate date){
        super(String.format("The date entered is invalid: %s", date.toString()));
    }
}
