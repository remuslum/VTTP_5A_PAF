package sg.nus.edu.iss.vttp_5a_day25_workshop.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_day25_workshop.jsonparser.JSONOrderParser;
import sg.nus.edu.iss.vttp_5a_day25_workshop.model.Order;
import sg.nus.edu.iss.vttp_5a_day25_workshop.model.OrderDetails;
import sg.nus.edu.iss.vttp_5a_day25_workshop.util.RedisConstants;

@Repository
public class OrderRepo {

    @Value("${app.name}")
    private String appName;
    
    @Autowired
    @Qualifier(RedisConstants.REDISTEMPLATEORDER)
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    @Qualifier(RedisConstants.ORDERTOPIC)
    private ChannelTopic orderTopic;

    @Autowired
    JSONOrderParser jsonparser;

    private ListOperations<String, String> listOperations;

    @PostConstruct
    public void initiateListOps(){
        this.listOperations = redisTemplate.opsForList();
    }

    public boolean sendJsonToClient(Order order, List<OrderDetails> orderDetails){
        JsonObject jsonObject = jsonparser.convertOrderToJson(order, orderDetails);
        listOperations.rightPush(appName, jsonObject.toString());
        return redisTemplate.convertAndSend(orderTopic.getTopic(), 
        listOperations.leftPop(RedisConstants.REDISKEYNAME)) > 0;
    }

}
