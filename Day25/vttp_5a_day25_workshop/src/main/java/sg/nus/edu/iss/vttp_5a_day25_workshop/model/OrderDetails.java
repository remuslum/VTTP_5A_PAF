package sg.nus.edu.iss.vttp_5a_day25_workshop.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.MultiValueMap;

import sg.nus.edu.iss.vttp_5a_day25_workshop.util.DecimalRounder;

public class OrderDetails {
    private int id;
    private String product;
    private float unitPrice;
    private float discount;
    private int quantity;

    public OrderDetails() {
    }

    public OrderDetails(int id, String product, float unitPrice, float discount, int quantity) {
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

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static List<OrderDetails> createOrderDetails(MultiValueMap<String, String> params){
        List<String> products = params.get("product");
        List<String> unitPrice = params.get("unitPrice");
        List<String> discount = params.get("discount");
        List<String> quantity = params.get("quantity");

        List<OrderDetails> orderDetails = new ArrayList<>();

        for(int i = 0; i < products.size(); i++){
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setProduct(products.get(i));
            orderDetail.setDiscount(DecimalRounder.roundTo2DecimalPlaces(Float.parseFloat(discount.get(i))));
            orderDetail.setUnitPrice(DecimalRounder.roundTo2DecimalPlaces(Float.parseFloat(unitPrice.get(i))));
            orderDetail.setQuantity(Integer.parseInt(quantity.get(i)));
            orderDetails.add(orderDetail);
        }

        return orderDetails;
    }

    
}
