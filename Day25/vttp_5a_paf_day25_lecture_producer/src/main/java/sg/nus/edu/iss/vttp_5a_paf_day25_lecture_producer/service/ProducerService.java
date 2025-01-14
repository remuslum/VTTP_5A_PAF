package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.model.Todo;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.util.Names;

@Service
public class ProducerService {
    
    @Autowired
    @Qualifier(Names.TODO)
    private RedisTemplate<String, Todo> redisTemplate;

    @Value("${redis.topic1}")
    private String redisTopic1;

    public void sendMessage(Todo todo){
        redisTemplate.convertAndSend(redisTopic1, todo);
    }
    
}
