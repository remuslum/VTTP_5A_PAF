package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.service;

import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.model.Student;

@Service
public class StudentService {
    
    public void handleMessage(Student student){
        System.out.println(student);
    }
}
