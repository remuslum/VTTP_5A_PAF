package sg.nus.edu.iss.vttp_5a_day21_workshop.util;

public class Queries {
     public static final String QUERY_TO_GET_ALL_CUSTOMERS = 
        """
            select * from customers order by id limit ? offset ?; 
        """;

    public static final String QUERY_TO_GET_CUSTOMER = 
        """
            select * from customers where id = ?;
        """;

    public static final String QUERY_TO_GET_ORDERS_FROM_CUSTOMER = 
        """
            select * from orders o
            join customers c 
            on o.customer_id = c.id
            where c.id = ?
            order by o.id; 
        """;
}
