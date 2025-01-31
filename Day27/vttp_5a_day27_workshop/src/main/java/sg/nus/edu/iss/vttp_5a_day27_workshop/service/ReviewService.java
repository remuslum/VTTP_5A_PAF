package sg.nus.edu.iss.vttp_5a_day27_workshop.service;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.InvalidRatingException;
import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.MissingValueException;
import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.TooManyArgumentsException;
import sg.nus.edu.iss.vttp_5a_day27_workshop.repo.ReviewRepo;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.COMMENT_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.EDITED_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.ID_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.NAME_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.POSTED_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.RATING_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.RATING_OBJECT_ID_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.USER_KEY;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepo reviewRepo;

    private final String TIMESTAMP_KEY="timestamp";

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

    public JsonObject getReview(int reviewId){
        Document review = reviewRepo.findGameByReviewId(reviewId);
        JsonObjectBuilder jsonObjectBuilder = getNecessaryInfo(review);

        if(review.containsKey(EDITED_KEY)){
            jsonObjectBuilder.add(EDITED_KEY, "true");
        } else {
            jsonObjectBuilder.add(EDITED_KEY, "false");
        }

        jsonObjectBuilder.add(TIMESTAMP_KEY, LocalDateTime.now().toString());

        return jsonObjectBuilder.build();

    } 

    public JsonObject getReviewWithHistory(int reviewId){
        Document review = reviewRepo.findGameByReviewId(reviewId);
        JsonObjectBuilder jsonObjectBuilder = getNecessaryInfo(review);

        if(review.containsKey(EDITED_KEY)){
            List<Document> edited = review.getList(EDITED_KEY, Document.class);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            edited.forEach(e -> arrayBuilder.add(Json.createReader(new StringReader(e.toJson())).readObject()));
            jsonObjectBuilder.add(EDITED_KEY, arrayBuilder.build());
        } else {
            jsonObjectBuilder.add(EDITED_KEY, Json.createArrayBuilder().build());
        }

        jsonObjectBuilder.add(TIMESTAMP_KEY, LocalDateTime.now().toString());
        return jsonObjectBuilder.build();
    }

    private Object getLatest(String key, Document document){
        LocalDate timestamp = LocalDate.parse(document.getString(POSTED_KEY));
        List<Document> edited = document.getList(EDITED_KEY, Document.class);

        Object latest = document.get(key);
        for(Document d : edited){
            LocalDate editedDate = LocalDate.parse(d.getString(POSTED_KEY));
            if(editedDate.compareTo(timestamp) > 0){
                latest = d.get(key);
            }
        }
        return latest;
    }

    private JsonObjectBuilder getNecessaryInfo(Document review){
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        review.remove(RATING_OBJECT_ID_KEY);

        int rating = (int) getLatest(RATING_KEY, review);
        String comment = getLatest(COMMENT_KEY, review).toString();
        
        jsonObjectBuilder.add(USER_KEY, review.getString(USER_KEY)).add(RATING_KEY, rating).add(COMMENT_KEY, comment)
        .add(ID_KEY, review.getInteger(ID_KEY)).add(POSTED_KEY, review.getString(POSTED_KEY)).add(NAME_KEY, review.getString(NAME_KEY));

        return jsonObjectBuilder;
    }
}
