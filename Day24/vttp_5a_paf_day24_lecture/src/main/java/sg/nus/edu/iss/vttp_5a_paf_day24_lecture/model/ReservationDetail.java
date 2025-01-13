package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model;

public class ReservationDetail {
    private int id;
    private Book book;
    private Reservation reservation;
    
    public ReservationDetail() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public Reservation getReservation() {
        return reservation;
    }
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    

}
