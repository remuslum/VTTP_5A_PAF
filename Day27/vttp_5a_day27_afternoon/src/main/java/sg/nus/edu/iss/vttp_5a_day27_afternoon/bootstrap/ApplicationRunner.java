package sg.nus.edu.iss.vttp_5a_day27_afternoon.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import sg.nus.edu.iss.vttp_5a_day27_afternoon.JSONHandler.JsonHandler;
import sg.nus.edu.iss.vttp_5a_day27_afternoon.readfile.CommentFileReader;
import sg.nus.edu.iss.vttp_5a_day27_afternoon.repo.CommentRepository;

@Component
public class ApplicationRunner implements CommandLineRunner{

    @Autowired
    private JsonHandler jsonHandler;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void run(String... args) throws Exception {
        for(String arg:args){
            if(arg.contains("--load=")){
                String fileName = arg.subSequence(7, arg.length()).toString();
                CommentFileReader commentFileReader = new CommentFileReader(fileName);
                String collectionName = commentFileReader.getFileNameWithoutJson();

                commentRepository.createAndLoadIntoCollection(collectionName, jsonHandler.renameCId(commentFileReader.readFile()));
            }
        }
    }
    
}
