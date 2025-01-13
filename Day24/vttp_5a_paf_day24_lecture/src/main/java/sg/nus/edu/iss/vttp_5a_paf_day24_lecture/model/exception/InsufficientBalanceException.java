package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception;

public class InsufficientBalanceException extends RuntimeException{
    
    public InsufficientBalanceException(String message){
        super(message);
    }

    public InsufficientBalanceException(String message, Throwable throwable){
        super(message, throwable);
    }
}
