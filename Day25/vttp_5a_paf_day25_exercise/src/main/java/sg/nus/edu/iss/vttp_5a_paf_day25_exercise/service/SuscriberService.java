package sg.nus.edu.iss.vttp_5a_paf_day25_exercise.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class SuscriberService implements MessageListener{

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // Directly cast the bytes into a string
        String data = new String(message.getBody());
        System.out.println(data);
    }
    

}
