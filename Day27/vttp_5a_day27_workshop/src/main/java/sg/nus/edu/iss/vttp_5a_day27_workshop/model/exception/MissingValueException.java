package sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception;

public class MissingValueException extends RuntimeException{
    public MissingValueException(String message){
        super(message);
    }
}
