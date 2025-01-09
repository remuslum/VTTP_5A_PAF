package sg.nus.edu.iss.vttp_5a_day21_workshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import sg.nus.edu.iss.vttp_5a_day21_workshop.JSON.JsonParserOrder;
import sg.nus.edu.iss.vttp_5a_day21_workshop.model.Order;
import sg.nus.edu.iss.vttp_5a_day21_workshop.repo.OrderRepo;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private JsonParserOrder jsonParserOrder;

    public JsonArray getAllOrders(String customerID){
        Optional<List<Order>> ordersOptional = orderRepo.getAllOrders(customerID);
        JsonArray orders = ordersOptional
        .map((value) -> jsonParserOrder.convertOrderListToJSON(value))
        .orElseGet(() -> Json.createArrayBuilder().build());
        return orders;
    }
}
