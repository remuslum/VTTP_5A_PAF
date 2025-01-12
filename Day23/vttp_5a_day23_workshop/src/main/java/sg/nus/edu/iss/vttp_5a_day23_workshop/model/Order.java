package sg.nus.edu.iss.vttp_5a_day23_workshop.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Order {
    private int id;
    private String orderDate;
    private int customerId;
    private float totalPrice;
    private float costPrice;
    
    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public static Order createOrder(SqlRowSet rowSet){
        Order order = new Order();
        order.setId(rowSet.getInt("id"));
        // No default value is set for order_date and customer_id
        order.setOrderDate(
            Optional.ofNullable(LocalDateTime.parse(rowSet.getString("order_date"))
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .orElse(""));
        order.setCustomerId(Optional.ofNullable(rowSet.getInt("customer_id")).orElse(-1));
        order.setTotalPrice(rowSet.getFloat("total_sum"));
        order.setCostPrice(rowSet.getFloat("total_cost"));
        return order;
    }
}
