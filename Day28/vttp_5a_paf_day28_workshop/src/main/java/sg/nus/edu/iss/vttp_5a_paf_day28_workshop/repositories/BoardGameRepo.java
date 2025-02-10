package sg.nus.edu.iss.vttp_5a_paf_day28_workshop.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
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
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_COMMENT;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_MAX_RATING;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_MIN_RATING;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_RATING;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_REVIEW_GAME_ID;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_REVIEW_ID;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_REVIEW_RATING;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_REVIEW_USER;

@Repository
public class BoardGameRepo {

    private static final String DOLLAR_SIGN="$";
    private static final String FULL_STOP=".";
    
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

    //     db.getCollection("games").aggregate([
    //     {
    //         $lookup : {
    //             from : "reviews",
    //             localField : "gid",
    //             foreignField : "ID",
    //             as : "reviews",
    //             pipeline :[{
    //                 $sort : {
    //                     rating : -1
    //                     }
    //                 }   
    //             ]
    //         }
    //     },{
    //         $addFields: {
    //             maxRating: { $max: "$reviews.rating" }
    //         }
    //     },
    //     {
    //         $set: {
    //             reviews: {
    //                 $filter: {
    //                     input: "$reviews",
    //                     as: "review",
    //                     cond: { $eq: ["$$review.rating", "$maxRating"] }
    //                 }
    //             }
    //         }
    //     },
    //     {
    //         $unwind : "$reviews"
    //     },{
    //         $group : {
    //             _id:"$gid",
    //             games: {
    //                 $push : {
    //                     _id : "$gid",
    //                     name : "$name",
    //                     rating : "$reviews.rating",
    //                     user : "$reviews.user",
    //                     comment : "$reviews.comment",
    //                     review_id:"$reviews._id"
    //                 }
                    
    //             }
    //         }
    //     }
    // ])
    public List<Document> getHighestRatedComments(){
        LookupOperation lookupOperation = Aggregation.lookup(C_REVIEWS, F_GAME_ID, F_REVIEW_GAME_ID, C_REVIEWS);
        SortOperation sortbyRating = Aggregation.sort(Sort.Direction.DESC,F_REVIEW_RATING);
        AddFieldsOperation addMaxRating = Aggregation.addFields().addFieldWithValue(F_MAX_RATING, new Document("$max","$reviews.rating")).build();
        // context is a parameter that provides the current aggregation context and is used to manipulate fields or query references in the aggregation pipeline.
        // In this case, it’s not explicitly used, but it's part of the interface that allows for further customization.
         AggregationOperation filterMaxRating = context -> new Document("$set",
                new Document("reviews",
                        new Document("$filter",
                                new Document("input", "$reviews")
                                        .append("as", "review")
                                        .append("cond", new Document("$eq", List.of("$$review.rating", "$maxRating")))
                        )
                )
        );
        UnwindOperation unwindOperation = Aggregation.unwind(C_REVIEWS);
        GroupOperation groupOperation = Aggregation.group(F_ID).push(new Document().append(F_NAME,DOLLAR_SIGN+F_NAME).append(F_RATING, DOLLAR_SIGN+F_REVIEW_RATING)
        .append(F_REVIEW_USER,DOLLAR_SIGN+C_REVIEWS+FULL_STOP+F_REVIEW_USER).append(F_REVIEW_ID,DOLLAR_SIGN+C_REVIEWS+FULL_STOP+F_ID)
        .append(F_COMMENT,DOLLAR_SIGN+C_REVIEWS+FULL_STOP+F_COMMENT)).as(C_GAMES);

        Aggregation aggregationOperation = Aggregation.newAggregation(lookupOperation,sortbyRating,addMaxRating,filterMaxRating,unwindOperation,
        groupOperation);

        return mongoTemplate.aggregate(aggregationOperation, C_GAMES, Document.class).getMappedResults();
    }

    public List<Document> getLowestRatedComments(){
        LookupOperation lookupOperation = Aggregation.lookup(C_REVIEWS, F_GAME_ID, F_REVIEW_GAME_ID, C_REVIEWS);
        SortOperation sortbyRating = Aggregation.sort(Sort.Direction.ASC,F_REVIEW_RATING);
        AddFieldsOperation addMaxRating = Aggregation.addFields().addFieldWithValue(F_MIN_RATING, new Document("$min","$reviews.rating")).build();

         AggregationOperation filterMaxRating = context -> new Document("$set",
                new Document("reviews",
                        new Document("$filter",
                                new Document("input", "$reviews")
                                        .append("as", "review")
                                        .append("cond", new Document("$eq", List.of("$$review.rating", "$minRating")))
                        )
                )
        );
        UnwindOperation unwindOperation = Aggregation.unwind(C_REVIEWS);
        GroupOperation groupOperation = Aggregation.group(F_ID).push(new Document().append(F_NAME,DOLLAR_SIGN+F_NAME).append(F_RATING, DOLLAR_SIGN+F_REVIEW_RATING)
        .append(F_REVIEW_USER,DOLLAR_SIGN+C_REVIEWS+FULL_STOP+F_REVIEW_USER).append(F_REVIEW_ID,DOLLAR_SIGN+C_REVIEWS+FULL_STOP+F_ID)
        .append(F_COMMENT,DOLLAR_SIGN+C_REVIEWS+FULL_STOP+F_COMMENT)).as(C_GAMES);

        Aggregation aggregationOperation = Aggregation.newAggregation(lookupOperation,sortbyRating,addMaxRating,filterMaxRating,unwindOperation,
        groupOperation);

        return mongoTemplate.aggregate(aggregationOperation, C_GAMES, Document.class).getMappedResults();
    }
}
