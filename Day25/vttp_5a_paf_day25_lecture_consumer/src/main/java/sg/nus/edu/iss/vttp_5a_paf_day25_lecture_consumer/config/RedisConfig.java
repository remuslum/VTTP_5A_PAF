package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.model.Order;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.model.Student;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.model.Todo;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.service.ConsumerService;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.service.OrderService;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.service.StudentService;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.util.Names;

@Configuration
public class RedisConfig {
    
    @Value("${redis.topic1}")
    private String redisTopic1;

    @Value("${redis.topic2}")
    private String redisTopic2;

    @Value("${redis.topic3}")
    private String redisTopic3;

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

    @Bean
    public RedisMessageListenerContainer listenerContainer(@Qualifier(Names.TODOQUALIFIER) MessageListenerAdapter messageListenerAdapter,
    RedisConnectionFactory connFac){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connFac);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(redisTopic1));

        return container;
    }

    @Bean(Names.TODOQUALIFIER)
    public MessageListenerAdapter listenerAdapter(ConsumerService consumerService){
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(consumerService);
        listenerAdapter.setSerializer(new Jackson2JsonRedisSerializer<>(Todo.class));
        return listenerAdapter;
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

    @Bean
    public RedisMessageListenerContainer listenerContainerStudent(@Qualifier(Names.STUDENTQUALIFIER) MessageListenerAdapter messageListenerAdapter,
    RedisConnectionFactory connFac){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connFac);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(redisTopic2));

        return container;
    }

    @Bean(Names.STUDENTQUALIFIER)
    public MessageListenerAdapter listenerAdapterStudent(StudentService studentService){
        // Adapter by default listens to the method handlemessage()
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(studentService);
        listenerAdapter.setSerializer(new Jackson2JsonRedisSerializer<>(Student.class));
        return listenerAdapter;
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
    public ChannelTopic topic() {
        return new ChannelTopic(redisTopic3);
    }

    private MessageListener messageListener;
    private RedisConnectionFactory redisConnFac;

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(messageListener);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(ChannelTopic topic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnFac);
        container.addMessageListener(messageListener, topic);
        return container;
    }
    

}
