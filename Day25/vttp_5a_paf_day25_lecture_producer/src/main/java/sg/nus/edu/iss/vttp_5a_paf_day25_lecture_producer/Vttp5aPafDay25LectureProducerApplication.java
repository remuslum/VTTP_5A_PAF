package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Vttp5aPafDay25LectureProducerApplication {

	// @Autowired
	// private static ProducerService producerService;

	public static void main(String[] args) {
		SpringApplication.run(Vttp5aPafDay25LectureProducerApplication.class, args);

		// for(int i = 0; i < 1000; i++){
		// 	Todo todo = new Todo();
		// 	todo.setId(i);
		// 	todo.setTask("Task " + i);
		// 	producerService.sendMessage(todo);
		// }
	}

}
