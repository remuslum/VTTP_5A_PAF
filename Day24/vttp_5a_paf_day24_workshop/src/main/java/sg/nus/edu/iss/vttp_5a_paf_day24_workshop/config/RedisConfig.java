package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.service.OrderService;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.util.RedisConstants;

@Configuration
public class RedisConfig {
    
    @Value("${redis.topic1}")
    private String orderTopic;

    @Bean(RedisConstants.REDISTEMPLATEORDER)
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connFac){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connFac);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        
        return redisTemplate;
    }

    @Bean(RedisConstants.ORDERTOPIC)
    public ChannelTopic createOrderTopic(){
        return ChannelTopic.of(orderTopic);
    }

    @Bean(RedisConstants.REDISCONTAINER)
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
    OrderService orderService, @Qualifier(RedisConstants.ORDERTOPIC) ChannelTopic orderTopic){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(orderService, orderTopic);

        return container;
    }
}
