package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.Order;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.util.Queries;

@Repository
public class OrderRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public int insertOrder(Order order){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = (Connection con) -> {
            PreparedStatement ps = con.prepareStatement(Queries.QUERY_TO_INSERT_INTO_ORDERS, new String[]{"id"});
            ps.setDate(1, order.getOrderDate());
            ps.setString(2, order.getCustomerName());
            ps.setString(3, order.getShipAddress());
            ps.setString(4, order.getNotes());
            return ps;
        };

        jdbcTemplate.update(psc, keyHolder);
        int orderId = keyHolder.getKey().intValue();
        return orderId;
    }
}
