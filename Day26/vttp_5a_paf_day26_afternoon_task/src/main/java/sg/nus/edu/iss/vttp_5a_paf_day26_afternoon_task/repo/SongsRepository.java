package sg.nus.edu.iss.vttp_5a_paf_day26_afternoon_task.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_paf_day26_afternoon_task.model.Song;
import static sg.nus.edu.iss.vttp_5a_paf_day26_afternoon_task.util.MongoConstants.C_NAME;
import static sg.nus.edu.iss.vttp_5a_paf_day26_afternoon_task.util.MongoConstants.F_YEAR;

@Repository
public class SongsRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Integer> getAllYears(){
        List<Integer> years = mongoTemplate.findDistinct(new Query(), F_YEAR, C_NAME,Integer.class);
        Collections.sort(years, Collections.reverseOrder());
        return years;
    } 
    
    public List<Song> findSongsByYear(int year){
        Query query = Query.query(Criteria.where(F_YEAR).is(year));
        query.fields().include("track_name", "artist(s)_name").exclude("_id");
        List<Document> songDocuments = mongoTemplate.find(query, Document.class, C_NAME);
        List<Song> songs = new ArrayList<>();

        songDocuments.forEach(doc -> {
            songs.add(Song.createSongFromDocument(doc));
        });

        return songs;
    }
}

