package sg.nus.edu.iss.vttp_5a_day21_workshop.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Order {
    private int id;
    private String shipName;
    private String shipAddress;
    private String shipCity;
    private float shippingFee;
    private float taxes;

    public Order() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getShipName() {
        return shipName;
    }
    public void setShipName(String shipName) {
        this.shipName = shipName;
    }
    public String getShipAddress() {
        return shipAddress;
    }
    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }
    public String getShipCity() {
        return shipCity;
    }
    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }
    public float getShippingFee() {
        return shippingFee;
    }
    public void setShippingFee(float shippingFee) {
        this.shippingFee = shippingFee;
    }
    public float getTaxes() {
        return taxes;
    }
    public void setTaxes(float taxes) {
        this.taxes = taxes;
    }

    public static Order createOrder(SqlRowSet rowSet){
        Order order = new Order();
        order.setId(rowSet.getInt("id"));
        order.setShipName(rowSet.getString("ship_name"));
        order.setShipAddress(rowSet.getString("ship_address"));
        order.setShipCity(rowSet.getString("ship_city"));
        order.setShippingFee(rowSet.getFloat("shipping_fee"));
        order.setTaxes(rowSet.getFloat("taxes"));
        return order;
    }

    
}
