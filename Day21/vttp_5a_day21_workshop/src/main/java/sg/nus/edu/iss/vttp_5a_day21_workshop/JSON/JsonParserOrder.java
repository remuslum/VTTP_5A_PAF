package sg.nus.edu.iss.vttp_5a_day21_workshop.JSON;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day21_workshop.model.Order;

@Component
public class JsonParserOrder {
    
    public JsonObject convertOrderToJSON(Order order){
        JsonObject jsonObject = Json.createObjectBuilder().add("id", order.getId()).add("ship_name", order.getShipName())
        .add("ship_address", order.getShipAddress()).add("ship_city", order.getShipCity())
        .add("shipping_fee", order.getShippingFee()).add("taxes", order.getTaxes()).build();
        return jsonObject;
    }

    public JsonArray convertOrderListToJSON(List<Order> orders){
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        orders.forEach((value) -> jsonArray.add(convertOrderToJSON(value)));
        return jsonArray.build();
    }
}
