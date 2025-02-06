package sg.nus.edu.iss.vttp_5a_paf_day28_workshop.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.C_GAMES;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_GAME_ID;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_GAME_ID_RENAMED;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_ID;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_IMAGE;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_IMAGE_RENAMED;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_NAME;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_RANKING;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_RANK_RENAMED;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_REVIEWS;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_URL;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_USERS_RATED;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_YEAR;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.C_REVIEWS;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_AVERAGE_RATING;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_RATING;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_REVIEWS_MAX_RATING;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_REVIEWS_MIN_RATING;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_REVIEW_GAME_ID;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_REVIEW_RATING;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_REVIEW_USER;

@Repository
public class BoardGameRepo {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    //     db.games.aggregate([
    //     {
    //         $match: {gid: 5}},
    //     {
    //         $lookup: {
    //             from: 'reviews',
    //             foreignField: 'ID',
    //             localField: 'gid',
    //             as: 'reviews'
    //         }
    //     },
    //     {
    //         $project: {
    //             'game_id':'$gid',
    //             'name':1,
    //             'year':1,
    //             'rank':'$ranking',
    //             'users_rated':1,
    //             'url':1,
    //             'thumbnail':'$image',
    //             'reviews':1,
    //             _id:0,
    //         }
    //     },
    // ]);
    
    public List<Document> getBoardGame(int gameId){
        MatchOperation matchOperation = Aggregation.match(Criteria.where(F_GAME_ID).is(gameId));
        LookupOperation lookupOperation = Aggregation.lookup(C_REVIEWS, F_GAME_ID, F_REVIEW_GAME_ID,F_REVIEWS);
        ProjectionOperation projectionOperation = Aggregation.project()
        .and(F_GAME_ID).as(F_GAME_ID_RENAMED).and(F_RANKING).as(F_RANK_RENAMED).and(F_IMAGE).as(F_IMAGE_RENAMED)
        .andInclude(F_NAME,F_YEAR,F_USERS_RATED,F_URL,F_REVIEWS).andExclude(F_ID);
        Aggregation aggregation = Aggregation.newAggregation(matchOperation,
        lookupOperation, projectionOperation);
        return mongoTemplate.aggregate(aggregation, C_GAMES, Document.class).getMappedResults();    
    }

    //     db.games.aggregate([
    //     {
    //         $match: {gid: 5}},
    //     {
    //         $lookup: {
    //             from: 'reviews',
    //             foreignField: 'ID',
    //             localField: 'gid',
    //             as: 'reviews'
    //         }
    //     },
    //     {
    //         $unwind:'$reviews'
    //     },
    //     {
    //         $group:{
    //             _id:'$gid',
    //             average: { $avg:
    //                 '$reviews.rating'
    //             }
    //         }
    //     }
    // ]);

    public List<Document> getAverage(int gameId){
        MatchOperation matchOperation = Aggregation.match(Criteria.where(F_GAME_ID).is(gameId));
        LookupOperation lookupOperation = Aggregation.lookup(C_REVIEWS, F_GAME_ID, F_REVIEW_GAME_ID,F_REVIEWS);
        UnwindOperation unwindOperation = Aggregation.unwind(F_REVIEWS);
        GroupOperation groupOperation = Aggregation.group(F_GAME_ID).avg(F_REVIEW_RATING).as(F_AVERAGE_RATING);

        Aggregation aggregation = Aggregation.newAggregation(matchOperation, lookupOperation, unwindOperation, groupOperation);
        return mongoTemplate.aggregate(aggregation, C_GAMES, Document.class).getMappedResults();
    }

    // db.reviews.aggregate([{
    //     $match : {
    //         user : {
    //             $regex : 'rem', $options : 'i'
    //             },
    //         rating : 8
    //         }
    //     }, {
    //         $lookup : {
    //             from : 'games',
    //             localField : 'ID',
    //             foreignField : 'gid',
    //             as : 'games'
    //         }
    //     }, {
    //         $project : {
    //             games : 1,
    //             _id : 0
    //         }
    //     }
    // ])
    public List<Document> getGames(String user, int rating){
        MatchOperation matchUser = Aggregation.match(Criteria.where(F_REVIEW_USER).is(user));
        MatchOperation matchRating = Aggregation.match(Criteria.where(F_RATING).is(rating));

        LookupOperation lookupFromGames = Aggregation.lookup(C_GAMES, F_REVIEW_GAME_ID, F_GAME_ID, C_GAMES);

        ProjectionOperation projectionOperation = Aggregation.project(C_GAMES).andExclude(F_ID);

        Aggregation aggregation = Aggregation.newAggregation(matchUser, matchRating, lookupFromGames, projectionOperation);
        return mongoTemplate.aggregate(aggregation, C_REVIEWS, Document.class).getMappedResults();
    }

    // db.reviews.aggregate([
    //     {
    //         $match : {
    //             user : {
    //                 $regex : <user>, $options : 'i'
    //             }
    //         }
    //     }, {
    //         $group : {
    //             _id:'$user',
    //             maxRating:{
    //                 $max : '$rating'
    //             },
    //             minRating:{
    //                 $min : '$rating'
    //             }
    //         }
    //     }
    // ])
    public Document getHighestAndLowestGameRating(String user){
        MatchOperation matchUser = Aggregation.match(Criteria.where(F_REVIEW_USER).regex(user, "i"));
        GroupOperation groupOperation = Aggregation.group(F_REVIEW_USER).max(F_RATING).as(F_REVIEWS_MAX_RATING).min(F_RATING).as(F_REVIEWS_MIN_RATING);
        Aggregation aggregation = Aggregation.newAggregation(matchUser, groupOperation);

        List<Document> results = mongoTemplate.aggregate(aggregation, C_REVIEWS, Document.class).getMappedResults();
        return results.get(0);
    }
}
