package sg.nus.edu.iss.vttp_5a_day22_workshop.repo;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day22_workshop.model.Event;
import sg.nus.edu.iss.vttp_5a_day22_workshop.util.Queries;

@Repository
public class RSVPRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<List<Event>> getAllEvents(){
        List<Event> events = new ArrayList<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_FOR_ALL_RSVP);
        while(rowSet.next()){
            events.add(Event.createEvent(rowSet));
        }
        if(events.isEmpty()){
            return Optional.empty();
        } else {
            return Optional.of(events);
        }
    }

    public Optional<Event> getEvent(String name){
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_FOR_SPECIFIC_RSVP, name);
        if(!rowSet.next()){
            return Optional.empty();
        } else {
            return Optional.of(Event.createEvent(rowSet));
        }
    }

    public boolean addEvent(String eventString){
        JsonObject jsonObject = Json.createReader(new StringReader(eventString)).readObject();
        int added = jdbcTemplate.update(Queries.QUERY_TO_INSERT, jsonObject.getString("name"),
        jsonObject.getString("email"), jsonObject.getString("phone"), jsonObject.getString("confirmDate"),
        jsonObject.getString("comments"));
        return added > 0;
    }
}
