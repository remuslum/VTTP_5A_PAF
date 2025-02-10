package sg.nus.edu.iss.vttp_5a_paf_day28_workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> getHighestRatingGames(){
        return ResponseEntity.ok(boardGameService.getHighestDocuments());
    }

    @GetMapping(path="/games/lowest", consumes="application/json", produces="application/json")
    public ResponseEntity<String> getLowestRatingGames(){
        return ResponseEntity.ok(boardGameService.getLowestDocuments());
    }
}
