package sg.nus.edu.iss.vttp_5a_day27_morning.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import sg.nus.edu.iss.vttp_5a_day27_morning.repo.TaskRepository;

@Component
public class ApplicationRunner implements CommandLineRunner{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        // taskRepository.insertTask();
        // taskRepository.anotherInsertTask();
        taskRepository.update();
        taskRepository.searchComments("enjoyable", "fun times");
    }
    
}
