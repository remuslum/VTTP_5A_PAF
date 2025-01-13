package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception;

public class AccountInactiveException extends RuntimeException{
    
    public AccountInactiveException(String message){
        super(message);
    }

    public AccountInactiveException(String message, Throwable throwable){
        super(message, throwable);
    }
}
