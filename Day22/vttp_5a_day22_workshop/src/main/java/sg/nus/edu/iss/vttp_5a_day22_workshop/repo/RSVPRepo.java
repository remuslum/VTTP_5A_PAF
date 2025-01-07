package sg.nus.edu.iss.vttp_5a_day22_workshop.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

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

    public boolean addEvent(JsonObject jsonObject){
        int added = jdbcTemplate.update(Queries.QUERY_TO_INSERT,jsonObject.getString("email"), 
        jsonObject.getString("phone"), jsonObject.getString("confirmDate"),
        jsonObject.getString("comments"));
        return added > 0;
    }

    public boolean addAndReplaceEvent(JsonObject jsonObject){
        int added = jdbcTemplate.update(Queries.QUERY_TO_INSERT_WITH_ID,jsonObject.getInt("id"),
        jsonObject.getString("name"),jsonObject.getString("email"), 
        jsonObject.getString("phone"), jsonObject.getString("confirmDate"),
        jsonObject.getString("comments"));
        return added > 0;
    }

    // public boolean updateDate(String variable){
    //     int added = jdbcTemplate.update(Queries.QUERY_TO_UPDATE, variable);
    //     return added > 0;
    // }

    public Optional<Integer> getTotalCount(){
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_TOTAL_COUNT);
        if(!rowSet.next()){
            return Optional.empty();
        } else {
            return Optional.of(rowSet.getInt("total_count"));
        }
    }
}
