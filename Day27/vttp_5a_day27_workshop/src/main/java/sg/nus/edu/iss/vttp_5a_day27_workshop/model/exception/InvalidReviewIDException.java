package sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception;

public class InvalidReviewIDException extends RuntimeException{
    public InvalidReviewIDException(String message){
        super(message);
    }
}
