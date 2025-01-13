package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception;

import java.time.LocalDateTime;

public class ErrorMessage {
    private int status;
    private String message;
    private LocalDateTime timeStamp;
    private String requestUri;

    public ErrorMessage(int status, String message, LocalDateTime timeStamp, String requestUri) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
        this.requestUri = requestUri;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    
}
