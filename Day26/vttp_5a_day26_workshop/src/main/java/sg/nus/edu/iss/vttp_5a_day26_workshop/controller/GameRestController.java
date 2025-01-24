package sg.nus.edu.iss.vttp_5a_day26_workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day26_workshop.service.GameService;
import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.GameConstants.COLUMN_GAME_ID;
import static sg.nus.edu.iss.vttp_5a_day26_workshop.util.GameConstants.COLUMN_GAME_RANK;

@RestController
@RequestMapping()
public class GameRestController {

    @Autowired
    private GameService gameService;
    
    @GetMapping(path="/games",produces="application/json")
    public ResponseEntity<String> getAllGames(@RequestParam(defaultValue = "25") int limit,
    @RequestParam(defaultValue="0") int offset){
        return ResponseEntity.ok().body(gameService.getGames(COLUMN_GAME_ID,limit, offset).toString());
    }

    @GetMapping(path="/games/rank",produces="application/json")
    public ResponseEntity<String> getAllGamesSortByRank(@RequestParam(defaultValue = "25") int limit,
    @RequestParam(defaultValue="0") int offset){
        return ResponseEntity.ok().body(gameService.getGames(COLUMN_GAME_RANK,limit, offset).toString());
    }

    @GetMapping(path="/game/{game-id}", produces="application/json")
    public ResponseEntity<String> getGame(@PathVariable("game-id") String gameId){
        JsonObject object = gameService.getGame(gameId);
        return object.containsKey("message") ? ResponseEntity.status(HttpStatusCode.valueOf(404)).body(object.toString())
        : ResponseEntity.status(HttpStatusCode.valueOf(201)).body(object.toString());
    }
}
