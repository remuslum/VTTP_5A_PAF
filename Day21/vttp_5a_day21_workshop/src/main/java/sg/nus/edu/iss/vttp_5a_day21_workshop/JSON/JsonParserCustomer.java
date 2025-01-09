package sg.nus.edu.iss.vttp_5a_day21_workshop.JSON;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day21_workshop.model.Customer;

@Component
public class JsonParserCustomer {
    
    public JsonObject convertCustomerToJSON(Customer customer){
        JsonObject jsonObject = Json.createObjectBuilder().add("id", customer.getId()).add("company", customer.getCompany())
        .add("name", customer.getName()).add("job_title", customer.getJobTitle()).add("business_phone", customer.getBusinessPhone())
        .add("address", customer.getAddress()).add("city", customer.getCity()).add("country_region", customer.getCountryRegion()).build();
        return jsonObject;
    }

    public JsonArray convertCustomerListToJSON(List<Customer> customers){
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        customers.forEach(c -> jsonArray.add(convertCustomerToJSON(c)));
        return jsonArray.build();
    }

    

}
