package sg.nus.edu.iss.vttp_5a_paf_day25_exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.nus.edu.iss.vttp_5a_paf_day25_exercise.service.MessagePoller;

@SpringBootApplication
public class Vttp5aPafDay25ExerciseApplication implements CommandLineRunner{

	@Autowired
	public static MessagePoller messagePoller;

	public static void main(String[] args) {
		SpringApplication.run(Vttp5aPafDay25ExerciseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		messagePoller.start();
	}

}
