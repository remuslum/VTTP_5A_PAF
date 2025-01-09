package sg.nus.edu.iss.vttp_5a_day21_workshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day21_workshop.service.CustomerService;
import sg.nus.edu.iss.vttp_5a_day21_workshop.service.OrderService;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;
    
    @GetMapping("/customers")
    public ResponseEntity<String> getCustomersList(@RequestParam MultiValueMap<String, String> params){
        Optional<String> limit = Optional.ofNullable(params.getFirst("limit"));
        Optional<String> offset = Optional.ofNullable(params.getFirst("offset"));

        JsonArray customerJsonArray = customerService.getCustomerList(limit, offset);
        int status = customerJsonArray.isEmpty() ? 404 : 201;
        return ResponseEntity.status(status).body(customerJsonArray.toString());
    }

    @GetMapping("/customer/{customerID}")
    public ResponseEntity<String> getCustomer(@PathVariable String customerID){
        JsonObject customerJson = customerService.getCustomer(customerID);
        int status = customerJson.isEmpty() ? 404 : 201;
        return ResponseEntity.status(status).body(customerJson.toString());
    }

    @GetMapping("/customer/{customerID}/orders")
    public ResponseEntity<String> getOrders(@PathVariable String customerID){
        JsonArray ordersJson = orderService.getAllOrders(customerID);
        int status = ordersJson.isEmpty() ? 404 : 201;
        return ResponseEntity.status(status).body(ordersJson.toString());
    }
}
