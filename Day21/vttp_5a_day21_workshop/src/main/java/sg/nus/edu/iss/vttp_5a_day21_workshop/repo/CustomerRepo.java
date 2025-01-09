package sg.nus.edu.iss.vttp_5a_day21_workshop.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_day21_workshop.model.Customer;
import sg.nus.edu.iss.vttp_5a_day21_workshop.util.Queries;

@Repository
public class CustomerRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<List<Customer>> getCustomersList(int limit, int offset){
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_TO_GET_ALL_CUSTOMERS, limit, offset);
        List<Customer> customerList = new ArrayList<>();
        while(rowSet.next()){
            customerList.add(Customer.createCustomer(rowSet));
        }

        // If query is wrong or table is empty, return empty Optional
        return customerList.isEmpty() ? Optional.empty() : Optional.of(customerList);
    }

    public Optional<Customer> getCustomer(String id){
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_TO_GET_CUSTOMER, id);

        return rowSet.next() ? Optional.of(Customer.createCustomer(rowSet)) : Optional.empty();
    }

    //Default value for limit is 5 and offset is 0
    
}
