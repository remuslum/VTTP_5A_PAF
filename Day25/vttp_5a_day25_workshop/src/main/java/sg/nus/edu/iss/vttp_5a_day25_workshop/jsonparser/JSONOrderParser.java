package sg.nus.edu.iss.vttp_5a_day25_workshop.jsonparser;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.nus.edu.iss.vttp_5a_day25_workshop.model.Order;
import sg.nus.edu.iss.vttp_5a_day25_workshop.model.OrderDetails;

@Component
public class JSONOrderParser {
    
    public JsonObject convertOrderToJson(Order order, List<OrderDetails> orderDetails){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("customer_name", order.getCustomerName()).add("ship_address", order.getShipAddress())
        .add("notes", order.getNotes()).add("tax", order.getTax()).add("order_date", order.getOrderDate().toString())
        .add("line_items", convertOrderDetails(orderDetails));

        return builder.build();
    }

    private JsonArray convertOrderDetails(List<OrderDetails> orderDetails){
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        orderDetails.forEach(orderDetail -> {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("product", orderDetail.getProduct()).add("unit_price", orderDetail.getUnitPrice())
            .add("discount", orderDetail.getDiscount()).add("quantity", orderDetail.getQuantity());
            arrayBuilder.add(builder.build());
        });

        return arrayBuilder.build();
    }
}
