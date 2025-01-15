package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.model.Todo;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.util.Names;

@Service
public class ToDoService {
    
    @Autowired
    @Qualifier(Names.TODOTEMPLATE)
    private RedisTemplate<String, Todo> redisTemplate;

    @Autowired
    @Qualifier(Names.TODOTOPIC)
    private ChannelTopic channelTopic;

    public long sendMessage(Todo todo){
        return redisTemplate.convertAndSend(channelTopic.getTopic(), todo);
    }
    
}
