package sg.nus.edu.iss.vttp_5a_day27_afternoon.bootstrap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
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
        Options option = new Options().addOption(
            Option.builder("load").longOpt("load").hasArg()
            .required(false).build());
        
        CommandLineParser parser = new DefaultParser();
        
        try {
            CommandLine command = parser.parse(option, args);
            if(command.hasOption("load")){
                String argValue = command.getOptionValue("load");
                
                CommentFileReader commentFileReader = new CommentFileReader(argValue);
                
                String collectionName = commentFileReader.getFileNameWithoutJson();

                commentRepository.createAndLoadIntoCollection(collectionName, jsonHandler.renameCId(commentFileReader.readFile()));
            }
        } catch (ParseException e){
            System.err.println("Error parsing command-line arguments: " + e.getMessage());
        }
        
    }
    
}
