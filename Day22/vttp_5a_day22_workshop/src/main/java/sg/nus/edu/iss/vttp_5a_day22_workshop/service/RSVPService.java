package sg.nus.edu.iss.vttp_5a_day22_workshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_day22_workshop.model.Event;
import sg.nus.edu.iss.vttp_5a_day22_workshop.repo.RSVPRepo;

@Service
public class RSVPService {
    
    @Autowired
    RSVPRepo rsvpRepo;

    public Optional<List<Event>> getAllEvents(){
        return rsvpRepo.getAllEvents();
    }

    public Optional<Event> getEvent(String name){
        return rsvpRepo.getEvent(name);
    }

    public boolean addEvent(String jsonString){
        return rsvpRepo.addEvent(jsonString);
    }
}
