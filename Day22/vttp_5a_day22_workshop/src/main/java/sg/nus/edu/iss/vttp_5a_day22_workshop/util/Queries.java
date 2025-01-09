package sg.nus.edu.iss.vttp_5a_day22_workshop.util;

public class Queries {
    public static final String QUERY_FOR_ALL_RSVP = 
        """
           select * from guests; 
        """;

    public static final String QUERY_FOR_SPECIFIC_RSVP = 
        """
            select * from guests where name = ?            
        """;

    public static final String QUERY_TO_INSERT =
        """
           insert into guests (email, phone, confirmation_date, comments)
           values (?,?,?,?); 
        """;

    public static final String QUERY_TO_INSERT_WITH_ID =
        """
            replace into guests (guest_id, name, email, phone, confirmation_date, comments)
            values (?,?,?,?,?,?); 
        """;

    public static final String QUERY_TO_UPDATE =
        """
            update guests set name = ?, email = ?, phone = ?, confirmation_date = ?, comments = ?
            where email = ?;
        """;

    public static final String QUERY_TOTAL_COUNT = 
        """
            select count(*) as total_count from guests;
        """;
}
