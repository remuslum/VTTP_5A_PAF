package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model;

import java.time.LocalDate;

import jakarta.json.JsonObject;

public class Order {
    private int orderId;
    private LocalDate orderDate;
    private String customerName;
    private String shipAddress;
    private String notes;
    private double tax;

    public Order() {
    }

    public Order(int orderId, LocalDate orderDate, String customerName, String shipAddress, String notes, float tax) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.shipAddress = shipAddress;
        this.notes = notes;
        this.tax = tax;
    }
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getShipAddress() {
        return shipAddress;
    }
    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public double getTax() {
        return tax;
    }
    public void setTax(double tax) {
        this.tax = tax;
    }

    public static Order createOrder(JsonObject jsonObject){
        Order order = new Order();
        order.setOrderDate(LocalDate.parse(jsonObject.getString("order_date")));
        order.setCustomerName(jsonObject.getString("customer_name"));
        order.setShipAddress(jsonObject.getString("ship_address"));
        order.setNotes(jsonObject.getString("notes"));
        order.setTax(jsonObject.getJsonNumber("tax").doubleValue());

        return order;
    }
}
