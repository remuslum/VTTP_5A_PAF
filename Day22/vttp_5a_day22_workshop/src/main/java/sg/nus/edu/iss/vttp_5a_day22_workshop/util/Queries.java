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
           replace into guests (name, email, phone, confirmation_date, comments)
           values (?,?,?,?,?); 
        """;

    public static final String QUERY_TO_INSERT_WITH_ID =
    """
        replace into guests (id,name, email, phone, confirmation_date, comments)
        values (?,?,?,?,?,?); 
    """;
}
