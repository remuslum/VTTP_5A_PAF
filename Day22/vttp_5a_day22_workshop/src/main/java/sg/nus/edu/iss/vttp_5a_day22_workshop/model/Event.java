package sg.nus.edu.iss.vttp_5a_day22_workshop.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Event {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String confirmationDate;
    private String comments;

    public Event() {
    }

    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getConfirmationDate() {
        return confirmationDate;
    }
    public void setConfirmationDate(String confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public static Event createEvent(SqlRowSet rowSet){
        Event event = new Event();
        event.setId(rowSet.getInt("guest_id"));
        event.setName(rowSet.getString("name"));
        event.setEmail(rowSet.getString("email"));
        event.setPhone(rowSet.getString("phone"));
        event.setConfirmationDate(rowSet.getString("confirmation_date"));
        event.setComments(rowSet.getString("comments"));
        return event;
    }

    
}
