package sg.nus.edu.iss.vttp_5a_paf_day28_workshop.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BoardGameRepo {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    // db.games.aggregate([
    // {
    //     $match: {gid: 5}},
    // {
    //     $lookup: {
    //         from: 'reviews',
    //         foreignField: 'ID',
    //         localField: 'gid',
    //         as: 'reviews'
    //     }
    // },
    // {
    //     $project: {
    //         'game_id':'$gid',
    //         'name':1,
    //         'year':1,
    //         'rank':'$ranking',
    //         'users_rated':1,
    //         'url':1,
    //         'thumbnail':'$image',
    //         'reviews':1,
    //         _id:0
    //     }
    // }
    // ]);

    public Document getBoardGame(int gameId){
        return new Document();
    }
}
