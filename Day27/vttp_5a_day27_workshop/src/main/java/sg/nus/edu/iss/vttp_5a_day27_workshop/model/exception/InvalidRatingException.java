package sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception;

public class InvalidRatingException extends RuntimeException {
    
    public InvalidRatingException(String message){
        super(message);
    }
}
