package sg.nus.edu.iss.vttp_5a_day27_workshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.edu.iss.vttp_5a_day27_workshop.model.exception.MissingValueException;
import sg.nus.edu.iss.vttp_5a_day27_workshop.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;
    
    @PostMapping(produces="application/json")
    public ResponseEntity<String> insertReview(@RequestBody MultiValueMap<String, String> params){
        String name = Optional.ofNullable(params.getFirst("name"))
        .orElseThrow(() -> new MissingValueException("You are missing the parameter: name"));

        int rating = Optional.ofNullable(Integer.valueOf(params.getFirst("rating")))
        .orElseThrow(() -> new MissingValueException("You are missing the parameter: rating"));

        String comment = Optional.ofNullable("comment").orElse("-");

        int gameId = Optional.ofNullable(Integer.valueOf(params.getFirst("game_id")))
        .orElseThrow(() -> new MissingValueException("You are missing the parameter: game_id"));

        return reviewService.insertReview(name, rating, comment, gameId) ? 
        ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Successfully inserted") :
        ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Insert unsuccessful");

    }

    @PutMapping(path="/{review-id}", produces="application/json")
    public ResponseEntity<String> updateReview(@RequestBody String payload, @PathVariable("review-id") int reviewId){
        return reviewService.updateReview(reviewId, payload) ? 
        ResponseEntity.status(HttpStatusCode.valueOf(201)).body("Successful update") :
        ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Unsuccessful update");
    }

    @GetMapping(path="/{review-id}")
    public ResponseEntity<String> findReview(@PathVariable("review-id") int reviewId){
        return ResponseEntity.ok().body(" ");
    }

}
