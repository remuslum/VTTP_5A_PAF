package sg.nus.edu.iss.vttp_5a_paf_day25_exercise.model;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import sg.nus.edu.iss.vttp_5a_paf_day25_exercise.util.Names;

public class ThreadWorker implements Runnable{

    @Autowired
    @Qualifier(Names.REDISTEMPLATE)
    private RedisTemplate<String, String> template;

    private String name;

    public ThreadWorker(RedisTemplate<String, String> template, String name){
        this.template = template;
        this.name = name;
    }

    @Override
    public void run() {
        ListOperations<String, String> listOps = template.opsForList();
        while(true){
            try {
                Optional<String> option = Optional.ofNullable(listOps.rightPop("myqueue", Duration.ofSeconds(30)));
                option.ifPresent((value) -> System.out.println(value));
            } catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
    }
    
}
