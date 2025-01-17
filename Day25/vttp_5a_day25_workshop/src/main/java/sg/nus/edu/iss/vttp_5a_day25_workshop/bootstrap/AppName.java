package sg.nus.edu.iss.vttp_5a_day25_workshop.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import sg.nus.edu.iss.vttp_5a_day25_workshop.util.RedisConstants;

@Component
public class AppName implements CommandLineRunner{

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    @Qualifier(RedisConstants.REDISTEMPLATEORDER)
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        if(args.length > 0){
            environment.getSystemProperties().put("app.name", args[0]);
            redisTemplate.opsForList().rightPush(RedisConstants.REDISKEYNAME, args[0]);
        }
    }
    
}
