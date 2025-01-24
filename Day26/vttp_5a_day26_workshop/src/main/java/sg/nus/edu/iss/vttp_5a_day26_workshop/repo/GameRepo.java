package sg.nus.edu.iss.vttp_5a_day26_workshop.repo;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.GameConstants.COLUMN_GAME_ID;
import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.GameConstants.COLUMN_GAME_NAME;
import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.GameConstants.COLUMN_ID;
import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.GameConstants.C_GAMES;

@Repository
public class GameRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    /* Mongo query:
     * db.games.find({},{gid:1,name:1,_id:0}).sort({gid:1}).limit(5)
     */
    public List<Document> getGameInfo(String column, int limit, int offset){
        Query query = new Query();
        // Limit the fields to just name and gid
        query.fields().include(COLUMN_GAME_ID, COLUMN_GAME_NAME).exclude(COLUMN_ID);
        // Sort and limit results
        query.with(Sort.by(Sort.Direction.ASC, column)).limit(limit).skip(offset);

        List<Document> games = mongoTemplate.find(query, Document.class, C_GAMES);

        return games;   
    }

    /* Mongo query:
     * db.games.find({"_id":<game-id>})
     */
    public Optional<Document> getGame(String gameId){
        Criteria criteria = Criteria.where(COLUMN_ID).is(gameId);
        Query query = Query.query(criteria);

        return Optional.ofNullable(mongoTemplate.findOne(query, Document.class, C_GAMES));
    }


    /* Mongo query:
     * db.games.countDocuments()
     */
    public long getTotalGames(){
        return mongoTemplate.getCollection(C_GAMES).countDocuments();
    }
}
