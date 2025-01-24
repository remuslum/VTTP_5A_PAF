package sg.nus.edu.iss.vttp_5a_day27_workshop.service;

import java.io.StringReader;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.InvalidRatingException;
import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.MissingValueException;
import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.TooManyArgumentsException;
import sg.nus.edu.iss.vttp_5a_day27_workshop.repo.ReviewRepo;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.COMMENT_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.POSTED_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.RATING_KEY;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepo reviewRepo;

    @Transactional
    public boolean insertReview(String name, int rating, String comment, int gameId){
        if(rating < 1 || rating > 10){
            throw new InvalidRatingException("The rating provided is not valid");
        } else {
            return reviewRepo.insertReview(name, rating, comment, gameId);
        }
    }

    @Transactional
    public boolean updateReview(int gameId, String payload){
        JsonObject object = Json.createReader(new StringReader(payload)).readObject();
        Set<String> keys = object.keySet();

        if(keys.size() > 3){
            throw new TooManyArgumentsException("Too many arguments, only a max of 3 is allowed");
        }

        if(!keys.contains(COMMENT_KEY)){
            throw new MissingValueException(String.format("Missing value: %s", COMMENT_KEY));
        } else if (!keys.contains(RATING_KEY)){
            throw new MissingValueException(String.format("Missing value: %s", RATING_KEY));
        } else if (!keys.contains(POSTED_KEY)){
            throw new MissingValueException(String.format("Missing value: %s", POSTED_KEY));
        }

        return reviewRepo.updateReview(gameId, payload);
        
    }
}
