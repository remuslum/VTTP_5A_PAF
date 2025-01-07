package sg.nus.edu.iss.vttp_5a_day22_workshop.components;

import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day22_workshop.model.Event;

@Component
public class JSONEventParser {
    
    public JsonObject convertEventToJson(Event event){
        JsonObject object = Json.createObjectBuilder().add("id", event.getId())
        .add("name", event.getName()).add("email", event.getEmail()).add("phone", event.getPhone())
        .add("confirmDate", event.getConfirmationDate()).add("comments", event.getComments()).build();
        return object;
    }
    
}
