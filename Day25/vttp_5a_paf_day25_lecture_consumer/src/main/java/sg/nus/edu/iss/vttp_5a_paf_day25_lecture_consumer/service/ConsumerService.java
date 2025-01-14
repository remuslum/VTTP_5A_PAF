package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.service;

import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.model.Todo;

@Service
public class ConsumerService {
    
    public void handleMessage(Todo todo){
        System.out.println(todo);
    }
}
