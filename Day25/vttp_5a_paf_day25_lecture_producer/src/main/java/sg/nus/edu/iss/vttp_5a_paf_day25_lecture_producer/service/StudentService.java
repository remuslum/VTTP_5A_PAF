package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.model.Student;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.util.Names;

@Service
public class StudentService {
    
    @Autowired
    @Qualifier(Names.STUDENTTEMPLATE)
    private RedisTemplate<String, Student> redisTemplateStudent;

    @Autowired
    @Qualifier(Names.STRINGTEMPLATE)
    private RedisTemplate<String, String> redisTemplateString;

    @Autowired
    @Qualifier(Names.STUDENTTOPIC)
    private ChannelTopic channelTopic;

    public long sendMessage(Student student){
        return redisTemplateStudent.convertAndSend(channelTopic.getTopic(), student);
    }

    public long sendMessage(String string){
        return redisTemplateString.convertAndSend(channelTopic.getTopic(), string);
    }
}

