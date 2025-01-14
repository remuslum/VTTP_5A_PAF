package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.util;

public class Queries {
    public static final String QUERY_TO_INSERT_INTO_ORDERS = 
    """
        insert into orders (order_date, customer_name, ship_address, notes) 
        values (?,?,?,?);   
    """;

    public static final String QUERY_TO_INSERT_INTO_ORDER_DETAILS =
    """
        insert into order_details (product, unit_price, discount, quantity)
        values (?,?,?,?)        
    """;
}
