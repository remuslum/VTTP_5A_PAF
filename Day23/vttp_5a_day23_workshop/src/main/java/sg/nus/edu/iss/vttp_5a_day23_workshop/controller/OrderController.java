package sg.nus.edu.iss.vttp_5a_day23_workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day23_workshop.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @GetMapping(path="/total/{order_id}", produces="application/json")
    public ResponseEntity<String> getOrderDetails(@PathVariable String order_id){
        JsonObject orderObject = orderService.getOrder(order_id);        
        if(orderObject.containsKey("error_message")){
            // JsonObject output = Json.createObjectBuilder().add("error_message", "Unable to retrieve order").build();
            return new ResponseEntity<>(orderObject.toString(), HttpStatusCode.valueOf(404));
        } else {

            return new ResponseEntity<>(orderObject.toString(), HttpStatusCode.valueOf(200));
        }
    }
}
