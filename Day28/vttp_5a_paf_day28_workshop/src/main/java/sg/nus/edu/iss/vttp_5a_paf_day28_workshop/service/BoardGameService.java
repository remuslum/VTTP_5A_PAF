package sg.nus.edu.iss.vttp_5a_paf_day28_workshop.service;

import java.io.StringReader;
import java.time.LocalDateTime;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import sg.nus.edu.iss.vttp_5a_paf_day28_workshop.repositories.BoardGameRepo;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_GAME_ID_RENAMED;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_IMAGE_RENAMED;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_NAME;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_RANK_RENAMED;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_REVIEWS;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_URL;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_USERS_RATED;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.GamesMongoConstants.F_YEAR;
import static sg.nus.edu.iss.vttp_5a_paf_day28_workshop.util.ReviewsMongoConstants.F_AVERAGE_RATING;

@Service
public class BoardGameService {
    
    @Autowired
    private BoardGameRepo boardGameRepo;

    public final String TIMESTAMP="timestamp";

    public String getBoardGame(int gameId){
        Document boardGameInfo = boardGameRepo.getBoardGame(gameId).get(0);
        Document boardGameAverage = boardGameRepo.getAverage(gameId).get(0);
        System.out.println(boardGameAverage);

        JsonArrayBuilder reviewsArray = Json.createArrayBuilder();
        boardGameInfo.getList(F_REVIEWS, Document.class).forEach(r -> {
            reviewsArray.add(Json.createReader(new StringReader(r.toJson())).readObject());
        });

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add(F_GAME_ID_RENAMED, boardGameInfo.getInteger(F_GAME_ID_RENAMED))
        .add(F_NAME, boardGameInfo.getString(F_NAME)).add(F_YEAR, boardGameInfo.getInteger(F_YEAR))
        .add(F_RANK_RENAMED, boardGameInfo.getInteger(F_RANK_RENAMED))
        .add(F_AVERAGE_RATING, boardGameAverage.getDouble(F_AVERAGE_RATING))
        .add(F_USERS_RATED, boardGameInfo.getInteger(F_USERS_RATED))
        .add(F_URL, boardGameInfo.getString(F_URL)).add(F_IMAGE_RENAMED, boardGameInfo.getString(F_IMAGE_RENAMED))
        .add(F_REVIEWS, reviewsArray.build()).add(TIMESTAMP, LocalDateTime.now().toString());

        return objectBuilder.build().toString();
    }
}
