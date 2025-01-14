package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.model.Student;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.util.Names;

@Service
public class StudentService {
    
    @Autowired
    @Qualifier(Names.STUDENT)
    private RedisTemplate<String, Student> redisTemplate;

    @Value("${redis.topic2}")
    private String topic2;

    
    public void sendMessage(Student student){
        redisTemplate.convertAndSend(topic2, student);
    }
}

