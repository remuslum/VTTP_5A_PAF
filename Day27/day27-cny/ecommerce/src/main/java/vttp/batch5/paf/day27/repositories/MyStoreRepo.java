package vttp.batch5.paf.day27.repositories;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.paf.day27.models.LineItem;
import vttp.batch5.paf.day27.models.PurchaseOrder;
import vttp.batch5.paf.day27.models.exceptions.InvalidValueException;
import static vttp.batch5.paf.day27.util.Queries.INSERT_LINE_ITEM;
import static vttp.batch5.paf.day27.util.Queries.INSERT_PURCHASE_ORDER;

@Repository
public class MyStoreRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public boolean insertPurchaseOrder(PurchaseOrder purchaseOrder){
        // Check for invalid date, delivery date must be in the future
        if(purchaseOrder.getDeliveryDate().before(new Date())){
            throw new InvalidValueException("Delivery Date cannot be in the past");
        }

        int added = jdbcTemplate.update(INSERT_PURCHASE_ORDER, purchaseOrder.getPoId(),
        purchaseOrder.getName(), purchaseOrder.getAddress(), purchaseOrder.getDeliveryDate());
        return added > 0;
    }

    public boolean insertLineItem(LineItem lineItem){
        //Quantty must be greater than 0
        if(lineItem.getQuantity() <= 0){
            throw new InvalidValueException("Quantity must be greater than 0");
        }

        if(lineItem.getUnitPrice() <= 0){
            throw new InvalidValueException("Unit Price must be greater than 0");
        }

        int added = jdbcTemplate.update(INSERT_LINE_ITEM, lineItem.getName(), lineItem.getQuantity(),
        lineItem.getUnitPrice(), lineItem.getPoId());
        return added > 0;
    }

}
