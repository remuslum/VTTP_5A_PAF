package vttp.batch5.paf.day23.JSON;

import org.springframework.stereotype.Component;

import jakarta.json.JsonObject;

@Component
public class JSONParser {

    public String getAttributeFromObject(JsonObject jsonObject, String attribute){
        return jsonObject.getString(attribute);
    }
}
