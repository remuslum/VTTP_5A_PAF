package sg.nus.edu.iss.vttp_5a_paf_day26_lecture.bootstrap;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import sg.nus.edu.iss.vttp_5a_paf_day26_lecture.repo.TvShowsRepository;

@Component
public class ApplicationStarter implements CommandLineRunner{
    
    @Autowired
    private TvShowsRepository tvShowsRepository;

    @Override
    public void run(String... args) {
        
        // Execute the query
        List<Document> result = tvShowsRepository.findSeriesByName("love");
        
        result.stream().limit(3).forEach(d -> {
            System.out.printf(">>>>>>>> %s\n", d.toJson());

            //genres: ["a","b","c"]
            // List<String> genres = d.getList("genres", String.class);
            // for(String g:genres){
            //     System.out.println(g);
            // }

            // Document schedule = (Document) d.get("schedule");
            // System.out.printf("time: %s\n", schedule.getString("time"));
        });

        List<Document> results2 = tvShowsRepository.findSeriesByRating(7.5f);
        results2.stream().limit(5).forEach(d -> {
            System.out.printf(">>>>>>>> %s\n", d.toJson());
        });

        List<String> result3 = tvShowsRepository.findTypeOfSeries();
        System.out.println(result3);
    }

    
}
