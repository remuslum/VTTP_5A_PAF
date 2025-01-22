package sg.nus.edu.iss.vttp_5a_day27_afternoon.readfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;

public class CommentFileReader {
    private String fileName;

    public CommentFileReader(String fileName){
        this.fileName = fileName;
    }

    public JsonArray readFile(){
        try {
            JsonReader reader = Json.createReader(new FileReader(fileName));
            return reader.readArray();
        } catch (FileNotFoundException e) {
            return Json.createArrayBuilder().add("file not found").build();
        }
    }

    public String getFileNameWithoutJson(){
        String nameOfFile = new File(fileName).getName();
        return nameOfFile.substring(0,nameOfFile.length() - 5);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
