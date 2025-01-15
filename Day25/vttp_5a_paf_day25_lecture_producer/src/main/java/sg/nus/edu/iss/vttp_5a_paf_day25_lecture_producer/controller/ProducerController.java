package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.model.Order;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.model.Student;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.model.Todo;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.service.OrderService;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.service.ToDoService;
import sg.nus.edu.iss.vttp_5a_paf_day25_lecture_producer.service.StudentService;

@RestController
@RequestMapping("/api/messages")
public class ProducerController {
    
    @Autowired
    private ToDoService toDoService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/todo")
    public ResponseEntity<String> sendMessage(@RequestBody Todo todo){
        long numClients = toDoService.sendMessage(todo);
        System.out.println(numClients);
        if(numClients < 1){
            return new ResponseEntity<>("Sending failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Message sent", HttpStatus.OK);
    }

    @PostMapping("/student")
    public ResponseEntity<String> sendMessage(@RequestBody Student student){
        long numClients = studentService.sendMessage(student);
        System.out.println(numClients);
        if(numClients < 1){
            return new ResponseEntity<>("Sending failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Message sent", HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<String> sendOrder(@RequestBody Order order){
        long numClients = orderService.publish(order);
        System.out.println(order);
        System.out.println(numClients);
        if(numClients < 1){
            return new ResponseEntity<>("Sending failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Message sent", HttpStatus.OK);
    }
}
