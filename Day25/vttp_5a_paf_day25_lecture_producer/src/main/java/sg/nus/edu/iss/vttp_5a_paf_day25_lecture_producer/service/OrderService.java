package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.model.Order;

@Service
public class OrderService {
    
    @Autowired
    private RedisTemplate<String, Order> redisTemplate;

    @Autowired
    private ChannelTopic channelTopic;

    public long publish(Order order){
        return redisTemplate.convertAndSend(channelTopic.getTopic(), order);
    }
}
