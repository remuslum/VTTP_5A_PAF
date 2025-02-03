package sg.nus.edu.iss.vttp_5a_paf_day28_exercise.repo;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.COMMENT_AND_GAME_LOOKUP_ALIAS;
import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.COMMENT_C_TEXT_KEY;
import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.COMMENT_GID_KEY;
import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.COMMENT_ID_KEY;
import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.COMMENT_RATING_KEY;
import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.COMMENT_USER_KEY;
import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.C_COMMENT_NAME;
import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.C_GAME_NAME;
import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.GAME_GID_KEY;
import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.GAME_NAME_ALIAS;
import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.GAME_NAME_KEY;

@Repository
public class CommentRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * db.comment.aggregate([{$match:{user:'GetFunkadelic'}}
     * ,{$lookup:{from:'games',foreignField:'gid',localField:'gid',as:'game'}}
     * ,{$unwind:'$game'},{$project:{rating:1,c_text:1,name:'$game.name',_id:0}}])
     */
    public List<Document> getUserComments(String userName){
        Criteria usernameCriteria = Criteria.where(COMMENT_USER_KEY).is(userName);

        MatchOperation matchOperation = Aggregation.match(usernameCriteria);
        LookupOperation lookupOperation = Aggregation.lookup(C_GAME_NAME, COMMENT_GID_KEY, GAME_GID_KEY, COMMENT_AND_GAME_LOOKUP_ALIAS);
        UnwindOperation unwindOperation = Aggregation.unwind(COMMENT_AND_GAME_LOOKUP_ALIAS);
        ProjectionOperation projectionOperation = Aggregation.project(COMMENT_RATING_KEY,COMMENT_C_TEXT_KEY)
        .and(GAME_NAME_KEY).as(GAME_NAME_ALIAS).andExclude(COMMENT_ID_KEY);
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, COMMENT_RATING_KEY);

        Aggregation pipeline = Aggregation.newAggregation(matchOperation,lookupOperation,unwindOperation,projectionOperation,sortOperation);

        return mongoTemplate.aggregate(pipeline, C_COMMENT_NAME,Document.class).getMappedResults();
    }

}
