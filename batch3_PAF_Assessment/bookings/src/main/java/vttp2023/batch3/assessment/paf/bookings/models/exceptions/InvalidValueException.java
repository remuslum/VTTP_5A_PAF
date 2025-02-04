package vttp2023.batch3.assessment.paf.bookings.models.exceptions;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message){
        super(message);
    }
}
