package vttp2023.batch3.assessment.paf.bookings.util;

public class ReservationSQLQueries {
    
    public static final String INSERT_RESERVATION = 
    """
        INSERT INTO reservations (resv_id, name, email, acc_id, arrival_date, duration)
        VALUES (?,?,?,?,?,?);        
    """;

}
