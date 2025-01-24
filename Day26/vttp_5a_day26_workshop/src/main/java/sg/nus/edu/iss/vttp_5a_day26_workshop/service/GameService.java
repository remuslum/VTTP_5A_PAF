package sg.nus.edu.iss.vttp_5a_day26_workshop.service;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day26_workshop.repo.GameRepo;
import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.AdditionalValues.GAMES;
import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.AdditionalValues.LIMIT;
import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.AdditionalValues.OFFSET;
import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.AdditionalValues.THUMBNAIL;
import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.AdditionalValues.TIMESTAMP;
import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.AdditionalValues.TOTAL;
import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.GameConstants.COLUMN_GAME_IMAGE;
import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.GameConstants.COLUMN_ID;


@Service
public class GameService {
    
    @Autowired
    private GameRepo gameRepo;

    

    public JsonObject getGames(String column, int limit, int offset){
        List<Document> games = gameRepo.getGameInfo(column,limit, offset);

        JsonArrayBuilder gameArray = Json.createArrayBuilder();
        for(Document d:games){
            JsonObject object = Json.createReader(new StringReader(d.toJson())).readObject();
            gameArray.add(object);
        }

        JsonObject gameObject = Json.createObjectBuilder().add(GAMES, gameArray.build())
        .add(OFFSET, offset).add(LIMIT, limit).add(TOTAL, gameRepo.getTotalGames())
        .add(TIMESTAMP,LocalDateTime.now().toString()).build();
        
        return gameObject;
    }

    public JsonObject getGame(String gameId){
        Document game = gameRepo.getGame(gameId).orElse(new Document("message", "Game not found"));
        if(!game.containsKey("message")){
            ObjectId objectId = game.getObjectId(COLUMN_ID);
            game.put(COLUMN_ID, objectId.toHexString());
            game.put(THUMBNAIL, game.getString(COLUMN_GAME_IMAGE));
            game.put(TIMESTAMP, LocalDateTime.now().toString());
            game.remove(COLUMN_GAME_IMAGE);
        }
        return Json.createReader(new StringReader(game.toJson())).readObject();
    }
}
