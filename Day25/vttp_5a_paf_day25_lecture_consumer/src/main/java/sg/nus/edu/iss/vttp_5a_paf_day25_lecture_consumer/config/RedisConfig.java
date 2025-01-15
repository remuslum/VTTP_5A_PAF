package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    /*
     * RedisTemplate is not required for listening
     * The required components are RedisMessageListenerContainer and MessageListenerAdapter
     */
    
    @Value("${redis.topic1}")
    private String redisTopic1;

    @Value("${redis.topic2}")
    private String redisTopic2;

    @Value("${redis.topic3}")
    private String redisTopic3;

    /*
     * Initialisation of JacksonSerializer for all 3 topics
     * Todo, Student and Order
     */

    @Bean
    public Jackson2JsonRedisSerializer<Todo> jackson2JsonRedisSerializer(){
        return new Jackson2JsonRedisSerializer<>(Todo.class);
    }

    @Bean
    public Jackson2JsonRedisSerializer<Student> jackson2JsonRedisSerializerStudent(){
        return new Jackson2JsonRedisSerializer<>(Student.class);
    }

    @Bean
    public Jackson2JsonRedisSerializer<Order> jackson2JsonRedisSerializerOrder(){
        return new Jackson2JsonRedisSerializer<>(Order.class);
    }

    /*
     * Creation of 3 Channel Topics based on the topics provided
     */

    @Bean(Names.TODOTOPIC)
    public ChannelTopic topicToDo() {
        return new ChannelTopic(redisTopic1);
    }

    @Bean(Names.STUDENTTOPIC)
    public ChannelTopic topicStudent() {
        return new ChannelTopic(redisTopic2);
    }

    @Bean(Names.ORDERTOPIC)
    public ChannelTopic topicOrder() {
        return new ChannelTopic(redisTopic3);
    }

    /*
     * Create an adapter based on each topic available
     * The adapters are distinguised based on their bean names
     */

    @Bean(Names.TODOADAPTER)
    public MessageListenerAdapter listenerAdapter(ConsumerService consumerService){
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(consumerService);
        listenerAdapter.setSerializer(new Jackson2JsonRedisSerializer<>(Todo.class));
        return listenerAdapter;
    }

    @Bean(Names.STUDENTADAPTER)
    public MessageListenerAdapter listenerAdapterStudent(StudentService studentService){
        /*
         * Must ensure that the class passed into new MessageListenerAdapter has the method handleMessage
         * By default, the handleMessage method is searched and implemented by the Adapter
         */
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(studentService);
        listenerAdapter.setSerializer(new Jackson2JsonRedisSerializer<>(Student.class));
        return listenerAdapter;
    }

    /*
     * In the RedisMessageListenerContainer, the topic is set here
     * If the method implements MessageListener interface, there is no need to create a MessageListenerAddapter
     * Note when creating the container, we pass in the class OrderService directly without creating an adapter
     * This is because orderService implements MessageListener
     */

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory, 
    OrderService orderService, @Qualifier(Names.TODOADAPTER) MessageListenerAdapter todoAdapter, 
    @Qualifier(Names.STUDENTADAPTER) MessageListenerAdapter studentAdapter, @Qualifier(Names.TODOTOPIC) ChannelTopic todoTopic,
    @Qualifier(Names.STUDENTTOPIC) ChannelTopic studentTopic, @Qualifier(Names.ORDERTOPIC) ChannelTopic orderTopic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        container.addMessageListener(todoAdapter, todoTopic);
        container.addMessageListener(studentAdapter, studentTopic);
        container.addMessageListener(orderService, new PatternTopic(orderTopic.getTopic()));

        return container;
    }
    

}
