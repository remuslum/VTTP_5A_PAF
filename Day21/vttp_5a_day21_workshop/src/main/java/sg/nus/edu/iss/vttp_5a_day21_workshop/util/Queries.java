package sg.nus.edu.iss.vttp_5a_day21_workshop.util;

public class Queries {
     public static final String QUERY_TO_GET_ALL_CUSTOMERS = 
        """
            select * from customers order by id limit ? offset ?; 
        """;

}
