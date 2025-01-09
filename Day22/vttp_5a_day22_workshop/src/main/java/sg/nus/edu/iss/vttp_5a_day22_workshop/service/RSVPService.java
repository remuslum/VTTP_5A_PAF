package sg.nus.edu.iss.vttp_5a_day22_workshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day22_workshop.components.JSONEventParser;
import sg.nus.edu.iss.vttp_5a_day22_workshop.model.Event;
import sg.nus.edu.iss.vttp_5a_day22_workshop.repo.RSVPRepo;

@Service
public class RSVPService {
    
    private final JsonObject errorObject = Json.createObjectBuilder().add("error_message","Unsucessful loading of RSVP").build();

    @Autowired
    RSVPRepo rsvpRepo;

    @Autowired
    JSONEventParser jsonEventParser;

    public JsonArray getAllEvents(){
        Optional<List<Event>> rsvpListOptional = rsvpRepo.getAllEvents();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        // map and orElseGet can only be used if the return types are the same class
        return rsvpListOptional
        .map((value) -> {
            value.forEach(v -> arrayBuilder.add(jsonEventParser.convertEventToJson(v)));
            return arrayBuilder.build();
        })
        .orElseGet(() -> arrayBuilder.build());
    }

    public JsonObject getEvent(String name){
        Optional<Event> event = rsvpRepo.getEvent(name);

        return event
        .map((value) -> jsonEventParser.convertEventToJson(value))
        .orElseGet(() -> errorObject);
    }

    public boolean addEvent(JsonObject jsonObject){
        return rsvpRepo.addEvent(jsonObject);
    }

    public boolean addAndReplaceEvent(JsonObject jsonObject){
        return rsvpRepo.addAndReplaceEvent(jsonObject);
    }

    public boolean updateData(JsonObject jsonObject, String email){
        return rsvpRepo.updateRSVP(jsonObject, email);
    }

    public Optional<Integer> getTotalCount(){
        return rsvpRepo.getTotalCount();
    }
}
