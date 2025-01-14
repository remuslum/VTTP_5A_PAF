package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.model.Order;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.util.Names;

@Service
public class OrderService implements MessageListener{
    
    @Autowired
    @Qualifier(Names.ORDER)
    private RedisTemplate<String, Order> redisTemplate;

    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        try {

            String orderData = new String(message.getBody());
            System.out.println(orderData);

            // using Json-P to map it back to object
            // call the API in day 24 using RestTemplate to write to MySQL database

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    
}
