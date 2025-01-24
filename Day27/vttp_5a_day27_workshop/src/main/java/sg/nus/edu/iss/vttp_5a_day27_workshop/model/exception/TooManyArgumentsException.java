package sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception;

public class TooManyArgumentsException extends RuntimeException{
    
    public TooManyArgumentsException(String message){
        super(message);
    }
}
