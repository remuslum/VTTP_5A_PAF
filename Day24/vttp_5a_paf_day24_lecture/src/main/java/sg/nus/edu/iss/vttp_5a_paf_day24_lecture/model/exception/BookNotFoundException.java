package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception;

public class BookNotFoundException extends RuntimeException{
    
    public BookNotFoundException(String message){
        super(message);
    }
}
