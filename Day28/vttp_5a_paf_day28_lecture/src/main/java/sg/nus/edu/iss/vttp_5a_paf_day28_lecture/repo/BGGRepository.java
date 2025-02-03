package sg.nus.edu.iss.vttp_5a_paf_day28_lecture.repo;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;

@Repository
public class BGGRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * db.games.aggregate([
     *      {$match:{name:{$regex:name, $options:'i'}}}
     * ])
     */
    public List<Document> findGamesByName(String name){
        Criteria criteria = Criteria.where("name").regex(name, "i");

        // create the aggregation stages
        MatchOperation matchName = Aggregation.match(criteria);

        // Project attribute
        ProjectionOperation projectFields = Aggregation.project("name","ranking","image");

        //Sort by ranking
        SortOperation sortByRanking = Aggregation.sort(Sort.Direction.DESC, "ranking");

        // Limit the results
        LimitOperation limitOperation = Aggregation.limit(3);

        Aggregation pipeline = Aggregation.newAggregation(matchName, projectFields, sortByRanking, limitOperation);

        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "games", Document.class);
        
        return results.getMappedResults();
    }

    public List<Document> groupCommentsByUser(){
        GroupOperation groupOperation = Aggregation.group("user").push
        (new BasicDBObject().append("gid", "$gid").append("text", "$c_text")).as("comments");

        LimitOperation limitOperation = Aggregation.limit(3);

        Aggregation pipeline = Aggregation.newAggregation(groupOperation, limitOperation);

        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline,"comment", Document.class);

        return results.getMappedResults();
    }
}
