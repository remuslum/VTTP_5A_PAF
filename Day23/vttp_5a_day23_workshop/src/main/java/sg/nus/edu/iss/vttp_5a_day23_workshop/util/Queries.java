package sg.nus.edu.iss.vttp_5a_day23_workshop.util;

public class Queries {
    
    public static final String QUERY_TO_GET_ORDER = 
    """
        select o.id, o.order_date, o.customer_id,
        (d.quantity * d.unit_price * (1 - d.discount)) as total_price,
        (d.quantity * p.standard_cost) as cost_price
        from orders o
        join order_details d on o.id = d.order_id
        join products p on d.product_id = p.id
        where o.id = ?
        order by cost_price desc;
    """;
}
