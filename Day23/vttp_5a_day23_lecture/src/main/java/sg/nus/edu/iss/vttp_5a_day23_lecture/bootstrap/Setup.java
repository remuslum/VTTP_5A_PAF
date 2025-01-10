package sg.nus.edu.iss.vttp_5a_day23_lecture.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Setup implements CommandLineRunner{
    
    @Override
    public void run(String... args){
        System.out.println(String.format("Number of elements: %d", args.length));

        for(String arg: args){
            System.out.println(arg);
        }
    }
}
