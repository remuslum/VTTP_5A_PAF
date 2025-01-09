package sg.nus.edu.iss.vttp_5a_day21_workshop.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_day21_workshop.model.Order;
import sg.nus.edu.iss.vttp_5a_day21_workshop.util.Queries;

@Repository
public class OrderRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<List<Order>> getAllOrders(String customerID){
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_TO_GET_ORDERS_FROM_CUSTOMER, customerID);
        List<Order> orders = new ArrayList<>();

        while(rowSet.next()){
            orders.add(Order.createOrder(rowSet));
        } 

        return orders.isEmpty() ? Optional.empty() : Optional.of(orders);
    }
}
