package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model;

import jakarta.json.JsonObject;

public class OrderDetails {
    private int id;
    private String product;
    private double unitPrice;
    private double discount;
    private int quantity;

    public OrderDetails() {
    }

    public OrderDetails(int id, String product, double unitPrice, double discount, int quantity) {
        this.id = id;
        this.product = product;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.quantity = quantity;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static OrderDetails createOrderDetails(JsonObject jsonObject){
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setProduct(jsonObject.getString("product"));
        orderDetails.setUnitPrice(jsonObject.getJsonNumber("unit_price").doubleValue());
        orderDetails.setDiscount(jsonObject.getJsonNumber("discount").doubleValue());
        orderDetails.setQuantity(jsonObject.getInt("quantity"));

        return orderDetails;
    }
}
