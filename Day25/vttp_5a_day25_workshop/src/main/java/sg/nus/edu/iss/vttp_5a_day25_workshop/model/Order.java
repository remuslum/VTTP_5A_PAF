package sg.nus.edu.iss.vttp_5a_day25_workshop.model;

import java.time.LocalDate;

import org.springframework.util.MultiValueMap;

import sg.nus.edu.iss.vttp_5a_day25_workshop.util.DecimalRounder;

public class Order {
    private int orderId;
    private LocalDate orderDate;
    private String customerName;
    private String shipAddress;
    private String notes;
    private float tax;


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



    public float getTax() {
        return tax;
    }



    public void setTax(float tax) {
        this.tax = tax;
    }

    public static Order createOrder(MultiValueMap<String, String> params){
        Order order = new Order();
        order.setOrderDate(LocalDate.parse(params.getFirst("orderDate")));
        order.setCustomerName(params.getFirst("customerName"));
        order.setShipAddress(params.getFirst("shipAddress"));
        order.setNotes(params.getFirst("notes"));

        // Round up before saving
        order.setTax(DecimalRounder.roundTo2DecimalPlaces(Float.parseFloat(params.getFirst("tax"))));

        return order;
    }
}
