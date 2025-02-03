package sg.nus.edu.iss.vttp_5a_paf_day28_lecture.bootstrap;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import sg.nus.edu.iss.vttp_5a_paf_day28_lecture.repo.BGGRepository;
import sg.nus.edu.iss.vttp_5a_paf_day28_lecture.repo.SeriesRepo;

@Component
public class AggregateExamples implements CommandLineRunner {
    
    @Autowired
    private BGGRepository bggRepository;

    @Autowired
    private SeriesRepo seriesRepo;

    @Override
    public void run(String... args) throws Exception {
        // List<Document> results = bggRepository.findGamesByName("demon");

        // results.forEach(d -> System.out.printf("name: %s", d));

        List<Document> results = bggRepository.groupCommentsByUser();

        results.forEach(d -> System.out.printf("name: %s", d));

        // List<Document> seriesResult = seriesRepo.getGenresCount();

        // seriesResult.forEach(d -> System.out.printf("name: %s", d));


    }

    
}
