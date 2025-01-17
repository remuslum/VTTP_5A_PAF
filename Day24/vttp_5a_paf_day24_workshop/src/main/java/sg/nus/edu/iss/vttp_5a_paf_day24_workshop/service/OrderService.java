package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.Order;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.OrderDetails;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.repo.OrderRepo;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.util.RedisConstants;

@Service
public class OrderService implements MessageListener{
    
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    @Qualifier(RedisConstants.REDISTEMPLATEORDER)
    private RedisTemplate<String, String> redisTemplate;

    @Transactional
    public boolean insertDetails(Order order, OrderDetails orderDetails){
        return orderRepo.addOrderAndOrderDetails(order, orderDetails);
    }

    @Override
    public void onMessage(@NonNull Message message, @Nullable byte[] pattern) {
        String messageString = new String(message.getBody());
        String value = redisTemplate.opsForList().leftPop(messageString);

        JsonObject jsonObject = Json.createReader(new StringReader(value)).readObject();
        JsonArray jsonArray = jsonObject.getJsonArray("line_items");

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for(int i = 0; i < jsonArray.size(); i++){
            orderDetailsList.add(OrderDetails.createOrderDetails(jsonArray.getJsonObject(i)));
        }

        orderRepo.addToSQL(Order.createOrder(jsonObject), orderDetailsList);
    }
}
