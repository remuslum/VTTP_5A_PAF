package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class InvalidValueException extends RuntimeException{
    private final Object item;
    
    public InvalidValueException(Object item){
        super(String.format("Invalid value entered: %s", item));
        this.item = item;
    }

    public <T extends Number> InvalidValueException(T item){
        super(String.format("Value must be greater than 0: %s", item));
        this.item = item;
    }
}
