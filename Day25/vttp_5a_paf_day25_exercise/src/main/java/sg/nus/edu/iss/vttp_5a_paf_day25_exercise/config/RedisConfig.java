package sg.nus.edu.iss.vttp_5a_paf_day25_exercise.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import sg.nus.edu.iss.vttp_5a_paf_day25_exercise.service.SuscriberService;
import sg.nus.edu.iss.vttp_5a_paf_day25_exercise.util.Names;

@Configuration
public class RedisConfig {
    
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private String redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Value("${redis.topic1}")
    private String orderTopic;

    @Autowired
    private SuscriberService suscriberService;

    public RedisConnectionFactory createConnectionFactory(){
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisPort);
        config.setDatabase(0);

        if(!redisUsername.equals("") && redisPassword.equals("")){
            config.setUsername(redisUsername);
            config.setPassword(redisPassword);
        }

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(config, jedisClient);
        jedisConnectionFactory.afterPropertiesSet();

        return jedisConnectionFactory;
    }

    @Bean(Names.REDISTEMPLATE)
    public RedisTemplate<String, String> redisTemplate(){
        RedisConnectionFactory redisConnectionFactory = createConnectionFactory();
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer createMessageListenerContainer(){
        RedisConnectionFactory redisConnectionFactory = createConnectionFactory();
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(suscriberService, ChannelTopic.of("mytopic"));

        return container;
    }
}
