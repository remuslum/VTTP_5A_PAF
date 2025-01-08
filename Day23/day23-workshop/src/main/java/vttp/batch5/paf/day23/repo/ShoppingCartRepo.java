package vttp.batch5.paf.day23.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.paf.day23.util.Queries;

@Repository
public class ShoppingCartRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean addCheckout(int checkoutId, String name, String deliveryDate){
        int added = jdbcTemplate.update(Queries.QUERY_TO_ADD_CHECKOUT, checkoutId, name, deliveryDate);
        return added > 0;
    }
    
    public boolean addPerson(String name, String address, int checkoutId){
        int added = jdbcTemplate.update(Queries.QUERY_TO_ADD_PERSON, name, address, checkoutId);
        return added > 0;
    }

    public boolean addItemToCart(String name, int quantity, double unitPrice, int checkoutId){
        int added = jdbcTemplate.update(Queries.QUERY_TO_ADD_ITEM_TO_CART, name, quantity, unitPrice, checkoutId);
        return added > 0;
    }

    // public boolean updatePersonCheckOutId(String name, int checkoutId){
    //     int added = jdbcTemplate.update(Queries.QUERY_TO_UPDATE_CHECKOUT_ID_PERSON, checkoutId, name);
    //     return added > 0;
    // }

    // public boolean updateCartCheckOutId(String name, int checkoutId){
    //     int added = jdbcTemplate.update(Queries.QUERY_TO_UPDATE_CHECKOUT_ID_CART, checkoutId, name);
    //     return added > 0;
    // }
}
