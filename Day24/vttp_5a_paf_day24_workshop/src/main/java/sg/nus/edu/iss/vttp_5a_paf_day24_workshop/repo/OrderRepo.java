package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.Order;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.OrderDetails;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.exception.InvalidDateException;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.exception.InvalidValueException;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.util.Queries;

@Repository
public class OrderRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public int insertOrder(Order order){
        LocalDate orderDate = getOptional(order.getOrderDate());
        String customerName = getOptional(order.getCustomerName());
        String shipAddress = getOptional(order.getShipAddress());
        String notes = getOptional(order.getNotes());

        if(orderDate.isAfter(LocalDate.now())){
            throw new InvalidDateException(orderDate);
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            PreparedStatementCreator psc = (Connection con) -> {
                PreparedStatement ps = con.prepareStatement(Queries.QUERY_TO_INSERT_INTO_ORDERS, new String[]{"id"});
                ps.setString(1, orderDate.toString());
                ps.setString(2, customerName);
                ps.setString(3, shipAddress);
                ps.setString(4, notes);
                return ps;
            };
    
            jdbcTemplate.update(psc, keyHolder);
            int orderId = keyHolder.getKey().intValue();
            return orderId;
        }
    }

    public boolean addOrderAndOrderDetails(Order order, OrderDetails orderDetails){
        int orderId = insertOrder(order);
        String product = getOptional(orderDetails.getProduct());
        double unitPrice = getOptional(orderDetails.getUnitPrice());
        double discount = getOptional(orderDetails.getDiscount());
        int quantity = getOptional(orderDetails.getQuantity());

        if(unitPrice <= 0.0){
            throw new InvalidValueException(unitPrice);
        } else if(discount < 0.0 || discount > 1.0){
            throw new InvalidValueException(discount);
        } else if (quantity < 0){
            throw new InvalidValueException(quantity);
        }

        return jdbcTemplate.update(Queries.QUERY_TO_INSERT_INTO_ORDER_DETAILS, product,
        unitPrice, discount, quantity, orderId) > 0;
    }

    private <T> T getOptional(T item){
        return Optional.ofNullable(item).orElseThrow(() -> new InvalidValueException(item));
    }

    public boolean addOrderDetails(OrderDetails orderDetails, int orderId){
        String product = getOptional(orderDetails.getProduct());
        double unitPrice = getOptional(orderDetails.getUnitPrice());
        double discount = getOptional(orderDetails.getDiscount());
        int quantity = getOptional(orderDetails.getQuantity());

        if(unitPrice <= 0.0){
            throw new InvalidValueException(unitPrice);
        } else if(discount < 0.0 || discount > 1.0){
            throw new InvalidValueException(discount);
        } else if (quantity < 0){
            throw new InvalidValueException(quantity);
        }

        return jdbcTemplate.update(Queries.QUERY_TO_INSERT_INTO_ORDER_DETAILS, product,
        unitPrice, discount, quantity, orderId) > 0;
    }

    public boolean addToSQL(Order order, List<OrderDetails> orderDetails){
        int orderId = insertOrder(order);
        boolean isAdded = false;
        for(OrderDetails orderDetail : orderDetails){
            isAdded = addOrderDetails(orderDetail, orderId);
            if(!isAdded){
                break;
            }
        }
        return isAdded;
    }
}
