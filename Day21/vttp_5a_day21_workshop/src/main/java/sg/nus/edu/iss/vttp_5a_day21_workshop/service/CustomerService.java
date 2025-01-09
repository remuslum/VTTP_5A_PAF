package sg.nus.edu.iss.vttp_5a_day21_workshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day21_workshop.JSON.JsonParserCustomer;
import sg.nus.edu.iss.vttp_5a_day21_workshop.model.Customer;
import sg.nus.edu.iss.vttp_5a_day21_workshop.repo.CustomerRepo;
import sg.nus.edu.iss.vttp_5a_day21_workshop.util.DefaultValues;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    JsonParserCustomer jsonParser;

    public JsonArray getCustomerList(Optional<String> limitOptional, Optional<String> offsetOptional){
        int limit = limitOptional
        .map((value) -> Integer.valueOf(value))
        .orElseGet(() -> DefaultValues.LIMIT.getValue());

        int offset = offsetOptional
        .map((value) -> Integer.valueOf(value))
        .orElseGet(() -> DefaultValues.OFFSET.getValue());
        
        return getCustomerListHelper(limit, offset);
    }

    public JsonObject getCustomer(String id){
        Optional<Customer> customer = customerRepo.getCustomer(id);
        JsonObject customerJson = customer
        .map((value) -> jsonParser.convertCustomerToJSON(value))
        .orElseGet(() -> Json.createObjectBuilder().build());
        return customerJson;
    }
    
    private JsonArray getCustomerListHelper(int limit, int offset){
        Optional<List<Customer>> customers = customerRepo.getCustomersList(limit, offset);

        // If list is empty, return an empty Json Array
        return customers
        .map((value) -> jsonParser.convertCustomerListToJSON(value))
        .orElseGet(() -> Json.createArrayBuilder().build());
    }
}
