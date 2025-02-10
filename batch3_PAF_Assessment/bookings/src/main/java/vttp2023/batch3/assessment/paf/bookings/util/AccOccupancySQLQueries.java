package vttp2023.batch3.assessment.paf.bookings.util;

public class AccOccupancySQLQueries {

    public static final String COLUMN_VACANCY = "vacancy";

    public static final String GET_VACANCY =
    """
        SELECT vacancy FROM acc_occupancy WHERE acc_id = ?;        
    """;


    public static final String UPDATE_VACANCY = 
    """
        UPDATE acc_occupancy SET vacancy = ? WHERE acc_id = ?;
    """;
}
