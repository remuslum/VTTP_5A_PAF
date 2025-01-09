package sg.nus.edu.iss.vttp_5a_day21_workshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonArray;
import sg.nus.edu.iss.vttp_5a_day21_workshop.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    
    @GetMapping("/customers")
    public ResponseEntity<String> getCustomersList(@RequestParam MultiValueMap<String, String> params){
        Optional<String> limit = Optional.ofNullable(params.getFirst("limit"));
        Optional<String> offset = Optional.ofNullable(params.getFirst("offset"));

        JsonArray jsonArray = customerService.getCustomerList(limit, offset);
        int status = jsonArray.isEmpty() ? 404 : 201;
        return ResponseEntity.status(status).body(jsonArray.toString());
    }
}
