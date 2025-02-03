package sg.nus.edu.iss.vttp_5a_paf_day28_exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.edu.iss.vttp_5a_paf_day28_exercise.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(path="/{user}", produces="application/json")
    public ResponseEntity<String> getUserComments(@PathVariable String user){
        return new ResponseEntity<>(commentService.getUserComments(user), HttpStatusCode.valueOf(200));
    }
}
