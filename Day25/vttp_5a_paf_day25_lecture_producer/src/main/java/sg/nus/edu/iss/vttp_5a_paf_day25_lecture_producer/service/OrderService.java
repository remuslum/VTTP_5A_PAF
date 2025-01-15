package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.model.Order;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.util.Names;

@Service
public class OrderService {
    
    @Autowired
    @Qualifier(Names.ORDERTEMPLATE)
    private RedisTemplate<String, Order> redisTemplateOrder;

    @Autowired
    @Qualifier(Names.STRINGTEMPLATE)
    private RedisTemplate<String, String> redisTemplateString;

    @Autowired
    @Qualifier(Names.ORDERTOPIC)
    private ChannelTopic channelTopic;

    public long publish(Order order){
        return redisTemplateOrder.convertAndSend(channelTopic.getTopic(), order);
    }

    public long publish(String string){
        return redisTemplateString.convertAndSend(channelTopic.getTopic(), string);
    }
}
