package sg.nus.edu.iss.vttp_5a_paf_day26_lecture.repo;

// Must remember what to import
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static sg.nus.edu.iss.vttp_5a_paf_day26_lecture.util.MyConstants.C_NAME;
import static sg.nus.edu.iss.vttp_5a_paf_day26_lecture.util.MyConstants.F_NAME;
import static sg.nus.edu.iss.vttp_5a_paf_day26_lecture.util.MyConstants.F_RATING_AVERAGE;
import static sg.nus.edu.iss.vttp_5a_paf_day26_lecture.util.MyConstants.F_TYPE;

@Repository
public class TvShowsRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * db.shows.find({
     *  name:{
     *      $regex: 'a name',
     *      $options: "i"
     *  }
     * })
     * In assessment, this is how to include the query
     */
    public List<Document> findSeriesByName(String name){

        //Create the predicate
        Criteria criteria = Criteria.where(F_NAME).regex(name, "i");

        // Create the query
        Query query = Query.query(criteria);
        //Fields to include and exclude
        query.fields()
        .include("name","id","summary","image.original").exclude("_id");

        // Perform the query
        List<Document> results = mongoTemplate.find(query, Document.class, "shows");
        return results;
    }

    /*
     * db.series.find({
     *  "rating.average": {
     *      $gte:8     
     *   } 
     * })
     * .sort({ "rating.average" : -1})
     * .limit(10)
     */
    public List<Document> findSeriesByRating(float rating){

        Criteria criteria = Criteria.where(F_RATING_AVERAGE).gte(rating);

        Query query = Query.query(criteria)
                    .with(Sort.by(Sort.Direction.DESC, F_RATING_AVERAGE))
                    .limit(5);
        query.fields().include("name", "id", "summary", "image.original").exclude("_id");

        return mongoTemplate.find(query, Document.class, C_NAME);
    }

    /*
     * db.series.distinct("type")
     */
    public List<String> findTypeOfSeries(){
        return mongoTemplate.findDistinct(new Query(), F_TYPE, C_NAME, String.class);
    }
}
