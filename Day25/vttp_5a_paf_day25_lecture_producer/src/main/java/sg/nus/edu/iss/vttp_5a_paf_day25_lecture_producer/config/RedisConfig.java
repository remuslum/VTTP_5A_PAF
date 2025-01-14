package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.model.Order;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.model.Student;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.model.Todo;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.util.Names;

@Configuration
public class RedisConfig {

    @Value("${redis.topic3}")
    private String orderTopic;
    
    @Bean(Names.TODO)
    public RedisTemplate<String, Todo> redisTemplate(RedisConnectionFactory connFac, 
    Jackson2JsonRedisSerializer<Todo> serializer){
        RedisTemplate<String, Todo> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connFac);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public Jackson2JsonRedisSerializer<Todo> jackson2JsonRedisSerializer(){
        return new Jackson2JsonRedisSerializer<>(Todo.class);
    }

    @Bean(Names.STUDENT)
    public RedisTemplate<String, Student> redisTemplateStudent(RedisConnectionFactory connFac, 
    Jackson2JsonRedisSerializer<Student> serializer){
        RedisTemplate<String, Student> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connFac);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public Jackson2JsonRedisSerializer<Student> jackson2JsonRedisSerializerStudent(){
        return new Jackson2JsonRedisSerializer<>(Student.class);
    }

    @Bean(Names.ORDER)
    public RedisTemplate<String, Order> redisTemplateOrder(RedisConnectionFactory connFac, 
    Jackson2JsonRedisSerializer<Order> serializer){
        RedisTemplate<String, Order> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connFac);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public Jackson2JsonRedisSerializer<Order> jackson2JsonRedisSerializerOrder(){
        return new Jackson2JsonRedisSerializer<>(Order.class);
    }

    @Bean
    public ChannelTopic topic(){
        return new ChannelTopic(orderTopic);
    }
}

