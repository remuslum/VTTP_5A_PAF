package sg.nus.edu.iss.vttp_5a_day23_workshop.JSON;

import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.nus.edu.iss.vttp_5a_day23_workshop.model.Order;

@Component
public class JsonParserOrder {
    
    public JsonObject convertOrderToJson(Order order){
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        
        jsonObjectBuilder.add("id", order.getId()).add("order_date", order.getOrderDate())
        .add("customer_id", order.getCustomerId()).add("total_price", order.getTotalPrice())
        .add("cost_price", order.getCostPrice());

        return jsonObjectBuilder.build();
    }
}
