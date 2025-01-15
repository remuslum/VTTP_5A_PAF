package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements MessageListener{
    

    @Override
    public void onMessage(@NonNull Message message, @Nullable byte[] pattern) {
        try {

            String orderData = new String(message.getBody());
            System.out.println(orderData);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    
}
