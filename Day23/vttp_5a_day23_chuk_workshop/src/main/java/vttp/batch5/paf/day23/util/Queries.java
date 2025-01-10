package vttp.batch5.paf.day23.util;

public class Queries {
    public static final String QUERY_TO_ADD_PERSON =
    """
        insert into person (name, address, checkout_id)
            values (?, ?, ?)        
    """;

    public static final String QUERY_TO_ADD_ITEM_TO_CART =
    """
        insert into cart (name, quantity, unitPrice, checkout_id)
            values (?, ?, ?, ?)        
    """;

    public static final String QUERY_TO_ADD_CHECKOUT = 
    """
        insert into checkout (checkout_id, name, deliveryDate)
            values (?, ?, ?)        
    """;

    public static final String QUERY_TO_UPDATE_CHECKOUT_ID_PERSON =
    """
        update person set checkout_id = ? where name = ?        
    """;

    public static final String QUERY_TO_UPDATE_CHECKOUT_ID_CART =
    """
        update cart set checkout_id = ? where name = ?   
    """;
}
