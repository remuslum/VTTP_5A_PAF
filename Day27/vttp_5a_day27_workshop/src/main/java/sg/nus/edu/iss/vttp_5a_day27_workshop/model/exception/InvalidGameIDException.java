package sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception;

public class InvalidGameIDException extends RuntimeException{
    
    public InvalidGameIDException(String message){
        super(message);
    }
}
