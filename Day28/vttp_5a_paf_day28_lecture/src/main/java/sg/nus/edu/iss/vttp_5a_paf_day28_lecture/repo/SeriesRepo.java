package sg.nus.edu.iss.vttp_5a_paf_day28_lecture.repo;

import java.util.List;

import org.apache.catalina.Pipeline;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.stereotype.Repository;

@Repository
public class SeriesRepo {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * db.shows.aggregate([
     *      {$unwind:'$genres'}
     *      {
     *          $group:{    
     *              _id:'$genres',
     *              count: {$sum:1}
     *          }         
     * }     
     * ])
     */
    public List<Document> getGenresCount(){
        UnwindOperation unwindOperation = Aggregation.unwind("genres");

        GroupOperation groupOperation = Aggregation.group("genres").count().as("count");

        Aggregation pipeline = Aggregation.newAggregation(unwindOperation, groupOperation);

        return mongoTemplate.aggregate(pipeline, "shows", Document.class).getMappedResults();
    }
}
