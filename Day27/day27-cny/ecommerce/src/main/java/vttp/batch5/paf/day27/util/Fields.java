package vttp.batch5.paf.day27.util;

public class Fields {
    //collection
    public static final String C_NAME="purchaseorders";

    //operations
    public static final String OPERATIONS_ADD="add";

    //event log
    public static final String EVENT_ID_KEY="event_id";
    public static final String TIMESTAMP_KEY="timestamp";
    public static final String FIELDS_KEY="fields";
    public static final String OPERATIONS_KEY="ops";
    public static final String TABLE_KEY="table";

    //purchase order
    public static final String PURCHASE_ORDER_NAME_KEY="name";
    public static final String PURCHASE_ORDER_ADDRESS_KEY="address";
    public static final String PURCHASE_ORDER_DELIVERYDATE_KEY="deliveryDate";
    public static final String PURCHASE_ORDER_LINEITEMS_KEY="lineItems";
    public static final String PURCHASE_ORDER_TABLE_KEY="table";
    public static final String PURCHASE_ORDER_TABLE_NAME="purchase_orders";

    // lineitem
    public static final String LINEITEM_NAME_KEY="name";
    public static final String LINEITEM_QUANTITY_KEY="quantity";
    public static final String LINEITEM_UNIT_PRICE_KEY="unitPrice";
    public static final String LINEITEM_TABLE_KEY="table";
    public static final String LINEITEM_TABLE_NAME="line_items";
}
