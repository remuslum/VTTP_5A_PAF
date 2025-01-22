package sg.nus.edu.iss.vttp_5a_day27_morning.repo;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@Repository
public class TaskRepository {

    public static final String C_NAME_TASKS="tasks";
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * db.tasks.insert({
     *  name:'Jogging',
     *  priority:1,
     * to_bring:['water','phone','insect repellent']}
     * )
     */
    public void insertTask(){
        Document document = new Document();
        document.put("name", "jogging");
        document.put("priority", 1);
        
        List<String> toBring = new ArrayList<>();
        toBring.add("water");
        toBring.add("phone");
        toBring.add("insect repellent");

        document.put("to_bring", toBring);

        System.out.printf(">>>>> to insert: %s\n", document.toJson());

        Document insertedDoc = mongoTemplate.insert(document, C_NAME_TASKS);

        System.out.printf(">>>>> to insert: %s\n", insertedDoc.toJson());
    }

    public void anotherInsertTask(){
        JsonArray friends = Json.createArrayBuilder().add("fred").add("barney").build();
        JsonObject toInsert = Json.createObjectBuilder().add("name", "henry")
                                .add("friends",friends).add("venue","Chinatown")
                                .add("priority",1).build();
        
        String jsonString = toInsert.toString();
        Document toInsertDoc = Document.parse(jsonString);
        var result = mongoTemplate.insert(toInsertDoc, C_NAME_TASKS);
        
        System.out.printf(">>> result: %s\n", result);

        String jsonStringFromDocument = result.toJson();
        JsonObject object = Json.createReader(new StringReader(jsonStringFromDocument)).readObject();

        System.out.printf(">>>> jsonObject: %s\n",object.toString());
    }

    public void update(){
        String id = "abc";

        Criteria criteria = Criteria.where("_id").is(id);
        Query query = Query.query(criteria);

        Update updateOps = new Update().set("duration","Five hours");

        UpdateResult updateResult = mongoTemplate.updateMulti(query, updateOps,Document.class,C_NAME_TASKS);

        System.out.printf(">>>>> updateResult: %d\n", updateResult.getModifiedCount());
    }

    public void searchComments(String... terms){
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingAny(terms).caseSensitive(true);

        TextQuery textQuery = TextQuery.queryText(textCriteria).includeScore("similarity").sortByScore();

        mongoTemplate.find(textQuery, Document.class, "comment")
        .stream().forEach(d -> {
            System.out.printf(">>>>>> %s\n\n", d.toJson());
        });
    }
}
