package sg.nus.edu.iss.vttp_5a_paf_day28_exercise.service;

import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.COMMENT_C_TEXT_KEY;
import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.COMMENT_RATING_KEY;
import static sg.nus.edu.iss.vttp_5a_paf_day28_exercise.util.CommentConstants.GAME_NAME_ALIAS;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import sg.nus.edu.iss.vttp_5a_paf_day28_exercise.repo.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    
    public String getUserComments(String user){
        List<Document> results = commentRepository.getUserComments(user);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        results.forEach(r -> {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add(COMMENT_RATING_KEY, r.getInteger(COMMENT_RATING_KEY)).add(COMMENT_C_TEXT_KEY, r.getString(COMMENT_C_TEXT_KEY))
            .add(GAME_NAME_ALIAS, r.getString(GAME_NAME_ALIAS));
            arrayBuilder.add(objectBuilder.build());
        });

        return arrayBuilder.build().toString();
    }
}
