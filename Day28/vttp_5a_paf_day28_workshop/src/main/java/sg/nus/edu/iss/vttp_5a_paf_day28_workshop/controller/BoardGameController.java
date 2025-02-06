package sg.nus.edu.iss.vttp_5a_paf_day28_workshop.controller;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_paf_day28_workshop.service.BoardGameService;

@RestController
@RequestMapping()
public class BoardGameController {
    
    @Autowired
    private BoardGameService boardGameService;

    @GetMapping(path="/game/{game_id}/reviews", produces="application/json")
    public ResponseEntity<String> getBoardGameAndReviews(@PathVariable int game_id){
        return ResponseEntity.ok(boardGameService.getBoardGame(game_id));
    }

    @GetMapping(path="/games/highest", consumes="application/json", produces="application/json")
    public ResponseEntity<String> getHighestRatingGames(@RequestBody String payload){
        JsonObject jsonObject = Json.createReader(new StringReader(payload)).readObject();
        return ResponseEntity.ok(boardGameService.getHighestGamesInfo(jsonObject.getString("user"), jsonObject.getString("rating")));
    }

    @GetMapping(path="/games/lowest", consumes="application/json", produces="application/json")
    public ResponseEntity<String> getLowestRatingGames(@RequestBody String payload){
        JsonObject jsonObject = Json.createReader(new StringReader(payload)).readObject();
        return ResponseEntity.ok(boardGameService.getLowestGamesInfo(jsonObject.getString("user"), jsonObject.getString("rating")));
    }
}
