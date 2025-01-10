package sg.nus.edu.iss.vttp_5a_day23_workshop.repo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_day23_workshop.model.Order;
import sg.nus.edu.iss.vttp_5a_day23_workshop.util.Queries;

@Repository
public class OrderRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public Optional<Order> getOrder(String orderId){
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_TO_GET_ORDER, orderId);
        
        return rowSet.next() ? Optional.of(Order.createOrder(rowSet)) : Optional.empty();
    }
}
