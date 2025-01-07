package sg.nus.edu.iss.vttp_5a_day22_workshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import sg.nus.edu.iss.vttp_5a_day22_workshop.components.JSONEventParser;
import sg.nus.edu.iss.vttp_5a_day22_workshop.model.Event;
import sg.nus.edu.iss.vttp_5a_day22_workshop.service.RSVPService;

@RestController
@RequestMapping("/api")
public class RSVPRestController {
    
    @Autowired
    RSVPService rsvpService;

    @Autowired
    JSONEventParser jsonEventParser;

    @GetMapping(path="/rsvps", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllEvents(){
        Optional<List<Event>> rsvpList = rsvpService.getAllEvents();
        ResponseEntity<String> response = rsvpList
        .map(events -> {
            JsonArrayBuilder eventsJson = Json.createArrayBuilder();
            events.forEach(e -> eventsJson.add(jsonEventParser.convertEventToJson(e)));
            return ResponseEntity.ok().body(eventsJson.build().toString());
        })
        .orElseGet(() -> {
            String body = "{\"message\": \"unsuccessful loading of RSVP\"}";
            return ResponseEntity.badRequest().body(body);
        });
        return response;
    }

    @GetMapping(path="/rsvp", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getEvent(@RequestParam String q){
        Optional<Event> event = rsvpService.getEvent(q);
        ResponseEntity<String> response = event
        .map((value) -> {
            String body = jsonEventParser.convertEventToJson(value).toString();
            return ResponseEntity.ok().body(body);
        })
        .orElseGet(() -> {
            String body = "{\"message\": \"unable to find guest\"}";
            return ResponseEntity.badRequest().body(body);
        });
        return response;
    }

    @PostMapping(path="/rsvp", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addEvent(@RequestBody String event){
        if(rsvpService.addEvent(event)){
            return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("Added successfully");
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Unsuccessful, please try again");
        }
    }


}
