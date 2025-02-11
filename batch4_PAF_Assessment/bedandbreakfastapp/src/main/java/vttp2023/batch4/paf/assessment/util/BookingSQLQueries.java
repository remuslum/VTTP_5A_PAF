package vttp2023.batch4.paf.assessment.util;

public class BookingSQLQueries {
    public static final String BOOKING_TABLE = "bookings";
    public static final String INSERT_BOOKING = 
    """
        INSERT INTO bookings (booking_id,listing_id,duration,email)
        VALUES (?,?,?,?);        
    """;
}
