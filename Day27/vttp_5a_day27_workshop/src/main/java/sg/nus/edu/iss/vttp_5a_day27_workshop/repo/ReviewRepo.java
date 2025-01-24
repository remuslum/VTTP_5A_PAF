package sg.nus.edu.iss.vttp_5a_day27_workshop.repo;

import java.io.StringReader;
import java.time.LocalDate;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.InvalidGameIDException;
import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.InvalidReviewIDException;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.GameConstants.COLUMN_GID;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.GameConstants.COLUMN_ID;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.GameConstants.COLUMN_NAME;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.GameConstants.C_GAME;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.COMMENT_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.C_REVIEW;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.EDITED_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.ID_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.NAME_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.POSTED_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.RATING_KEY;
import static sg.nus.edu.iss.vttp_5a_day27_workshop.util.ReviewConstants.USER_KEY;

@Repository
public class ReviewRepo {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean insertReview(String name, int rating, String comment, int gameId){
        Document game = findGame(gameId);

        Document review = new Document();
        review.append(USER_KEY, name).append(RATING_KEY, rating).append(COMMENT_KEY, comment)
        .append(ID_KEY, gameId).append(POSTED_KEY, LocalDate.now().toString())
        .append(NAME_KEY, game.get(COLUMN_NAME));

        Document insertTo = mongoTemplate.insert(review, C_REVIEW);
        return !insertTo.isEmpty();
    }

    public Document findGame(int gameId){
        Query query = Query.query(Criteria.where(COLUMN_GID).is(gameId));
        query.fields().include(COLUMN_NAME).exclude(COLUMN_ID);
        return Optional.ofNullable(mongoTemplate.findOne(query, Document.class, C_GAME))
        .orElseThrow(() -> new InvalidGameIDException("Game ID is invalid"));
    }

    public boolean updateReview(int reviewId, String payload){
        JsonObject object = Json.createReader(new StringReader(payload)).readObject();
        Document document = findGameByReviewId(reviewId);

        Query query = Query.query(Criteria.where(ID_KEY).is(reviewId));
        Update updateOps = new Update().push(EDITED_KEY).each(
            new Document().append(COMMENT_KEY, object.getString(COMMENT_KEY))
            .append(RATING_KEY, object.getInt(RATING_KEY))
            .append(POSTED_KEY, object.getString(POSTED_KEY)));
        UpdateResult updateResult = mongoTemplate.updateFirst(query, updateOps, Document.class, C_REVIEW);
        return updateResult.getModifiedCount() > 0;
    }

    public Document findGameByReviewId(int reviewId){
        Query query = Query.query(Criteria.where(ID_KEY).is(reviewId));
        return Optional.ofNullable(mongoTemplate.findOne(query, Document.class, C_REVIEW))
        .orElseThrow(() -> new InvalidReviewIDException("Review ID is invalid"));
    }
}
