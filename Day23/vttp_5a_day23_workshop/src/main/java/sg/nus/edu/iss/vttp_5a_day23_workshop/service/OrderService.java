package sg.nus.edu.iss.vttp_5a_day23_workshop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day23_workshop.JSON.JsonParserOrder;
import sg.nus.edu.iss.vttp_5a_day23_workshop.model.Order;
import sg.nus.edu.iss.vttp_5a_day23_workshop.repo.OrderRepo;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private JsonParserOrder jsonParserOrder;

    public JsonObject getOrder(String orderId){
        Optional<Order> orderOptional = orderRepo.getOrder(orderId);
        return orderOptional
        .map((value) -> jsonParserOrder.convertOrderToJson(value))
        .orElseGet(() -> Json.createObjectBuilder().add("error_message", "Unable to retrieve order").build());
    }
}
