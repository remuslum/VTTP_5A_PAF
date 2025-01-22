package sg.nus.edu.iss.vttp_5a_day27_afternoon.repo;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.client.model.Indexes;

import jakarta.json.JsonArray;

@Repository
public class CommentRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void createAndLoadIntoCollection(String collectionName, JsonArray jsonArray){
        dropAndCreateCollection(collectionName);
        insertIntoCollection(jsonArray, collectionName);
        createTextIndex(collectionName, "c_text");
    }
    
    private void dropAndCreateCollection(String collectionName){
        mongoTemplate.dropCollection(collectionName);
        mongoTemplate.createCollection(collectionName);
    }

    private void insertIntoCollection(JsonArray data, String collectionName){
        // JsonObject is immutable, Documents are mutable

        for(int i = 0; i < data.size(); i++){
            Document documentToInsert = Document.parse(data.getJsonObject(i).toString());
            mongoTemplate.insert(documentToInsert, collectionName);
        }
    }

    private void createTextIndex(String collectionName, String fieldName){
        mongoTemplate.getCollection(collectionName).createIndex(Indexes.text(fieldName));
    }
}
