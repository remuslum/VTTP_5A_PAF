package sg.nus.edu.iss.vttp_5a_paf_day25_exercise.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import sg.nus.edu.iss.vttp_5a_paf_day25_exercise.model.ThreadWorker;
import sg.nus.edu.iss.vttp_5a_paf_day25_exercise.util.Names;

@Component
public class MessagePoller {
    
    @Autowired
    @Qualifier(Names.REDISTEMPLATE)
    private RedisTemplate<String, String> template;

    @Async
    public void start(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new ThreadWorker(template, "task 01"));
        executorService.submit(new ThreadWorker(template, "task 02"));
    }
}
