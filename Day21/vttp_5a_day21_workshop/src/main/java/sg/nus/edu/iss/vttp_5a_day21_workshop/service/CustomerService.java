package sg.nus.edu.iss.vttp_5a_day21_workshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import sg.nus.edu.iss.vttp_5a_day21_workshop.JSON.JsonParser;
import sg.nus.edu.iss.vttp_5a_day21_workshop.model.Customer;
import sg.nus.edu.iss.vttp_5a_day21_workshop.repo.CustomerRepo;
import sg.nus.edu.iss.vttp_5a_day21_workshop.util.DefaultValues;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    JsonParser jsonParser;

    public JsonArray getCustomerList(Optional<String> limitOptional, Optional<String> offsetOptional){
        int limit = limitOptional
        .map((value) -> {
            return Integer.valueOf(value);
        })
        .orElseGet(() -> DefaultValues.LIMIT.getValue());

        int offset = offsetOptional
        .map((value) -> {
            return Integer.valueOf(value);
        })
        .orElseGet(() -> DefaultValues.OFFSET.getValue());
        
        return getCustomerListHelper(limit, offset);
    }
    
    private JsonArray getCustomerListHelper(int limit, int offset){
        Optional<List<Customer>> customers = customerRepo.getCustomersList(limit, offset);

        // If list is empty, return an empty Json Array
        return customers
        .map((value) -> {
            return jsonParser.convertCustomerListToJSON(value);
        })
        .orElseGet(() -> {
            return Json.createArrayBuilder().build();
        });
    }
}
