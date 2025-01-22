package sg.nus.edu.iss.vttp_5a_day27_afternoon.JSONHandler;

import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Component
public class JsonHandler {
    
    public JsonArray renameCId(JsonArray jsonArray){
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        // {"c_id":"091910b8","user":"PAYDIRT","rating":6,
        // "c_text":"A detailed tactical game on air and air to ground combat missions in the The Vietnam War/Second Indochina War.  Worth a look if the topic interests you.  The 2nd edition bookcase version is a cleaned up and better version.",
        // "gid":6228}

        for(int i = 0; i < jsonArray.size(); i++){
            JsonObject object = jsonArray.getJsonObject(i);

            JsonObject objectRenamed = Json.createObjectBuilder().add("_id", object.getString("c_id"))
            .add("user", object.getString("user")).add("rating",object.getInt("rating"))
            .add("c_text",object.getString("c_text")).add("gid",object.getInt("gid")).build();

            jsonArrayBuilder.add(objectRenamed);
        }

        return jsonArrayBuilder.build();
    }
}
