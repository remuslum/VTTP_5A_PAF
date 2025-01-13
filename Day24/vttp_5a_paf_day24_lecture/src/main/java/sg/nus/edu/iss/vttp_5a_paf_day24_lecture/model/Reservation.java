package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model;

import java.sql.Date;

public class Reservation {
    private int id;
    private String fullName;
    private Date reservationDate;

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    

    
}
