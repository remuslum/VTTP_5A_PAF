package sg.nus.edu.iss.vttp_5a_day22_workshop.controller;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day22_workshop.service.RSVPService;

@RestController
@RequestMapping("/api")
public class RSVPRestController {

    @Autowired
    RSVPService rsvpService;


    @GetMapping(path="/rsvps", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllEvents(){
        JsonArray rsvpList = rsvpService.getAllEvents();

        // If the list is a jsonarray, we return a 201 else a 404
        if(rsvpList.isEmpty()){
            return ResponseEntity.badRequest().body(rsvpList.toString());
        } else {
            return ResponseEntity.ok().body(rsvpList.toString());
        }
    }

    @GetMapping(path="/rsvp", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonObject> getEvent(@RequestParam String q){
        JsonObject eventObject = rsvpService.getEvent(q);
        int status = eventObject.containsKey("error_message") ? 404 : 201;
        return ResponseEntity.status(status).body(eventObject);
    }

    @PostMapping(path="/rsvp", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> addEvent(@RequestBody String event){
        JsonObject jsonObject = Json.createReader(new StringReader(event)).readObject();
        Optional<Integer> id = jsonObject.containsKey("guest_id") ? Optional.of(jsonObject.getInt("guest_id")) : Optional.empty();

        boolean isAdded = id
        .map(
            (value) -> {
                return rsvpService.addAndReplaceEvent(jsonObject);
            }
        )
        .orElseGet(
            () -> {
                return rsvpService.addEvent(jsonObject);
            }
        );
        
        int status;
        Map<String, String> output = new HashMap<>();
        if(!isAdded){
            output.put("message", "POST operation is unsuccessful");
            status = 404;
        } else {
            output.put("message", "POST operation successful");
            status = 201;
        }
        return ResponseEntity.status(status).body(output);
    }

    @PutMapping(path="/rsvp/{email}", produces="application/json")
    public ResponseEntity<Map<String, String>> updateRSVP(@PathVariable String email, @RequestBody String event){
        JsonObject eventJSON = Json.createReader(new StringReader(event)).readObject();
        boolean isUpdated = rsvpService.updateData(eventJSON, email);
        Map<String, String> output = new HashMap<>();
        if(isUpdated){
            output.put("message", "Update successful");
            return ResponseEntity.ok().body(output);
        } else {
            output.put("error_message","Unsuccessful operation");
            return ResponseEntity.badRequest().body(output);
        }
    }

    @GetMapping(path="/rsvp/count", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getTotalCount(){
        Optional<Integer> count = rsvpService.getTotalCount();
        Map<String, String> output = new HashMap<>();

        count.ifPresentOrElse((value) -> {
            output.put("total_events", String.valueOf(value));
        },
        () -> {
            output.put("error_message", "Operation unsuccessful");
        });

        int status = output.containsKey("total_events") ? 201 : 404;
        return ResponseEntity.status(status).body(output);
        
    }

}
