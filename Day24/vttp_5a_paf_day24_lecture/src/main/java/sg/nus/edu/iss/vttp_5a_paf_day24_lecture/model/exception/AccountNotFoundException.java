package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(String message){
        super(message);
    }

    public AccountNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}
