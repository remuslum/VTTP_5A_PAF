package vttp.batch5.paf.day27.util;

public class Queries {

    public static final String INSERT_PURCHASE_ORDER = 
        """
            INSERT INTO purchase_orders (po_id, name, address, delivery_date)
            VALUES (?,?,?,?);
        """;

    public static final String INSERT_LINE_ITEM = 
        """
            INSERT INTO line_items (name, quantity, unit_price, po_id)
            VALUES (?,?,?,?);   
        """;
}
