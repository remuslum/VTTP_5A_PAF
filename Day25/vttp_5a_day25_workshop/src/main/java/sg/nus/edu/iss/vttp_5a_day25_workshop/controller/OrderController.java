package sg.nus.edu.iss.vttp_5a_day25_workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.nus.edu.iss.vttp_5a_day25_workshop.service.OrderService;


@Controller
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public String getOrderPage(){
        return "order";
    }

    @PostMapping("/order")
    public String postMethodName(@RequestParam MultiValueMap<String, String> params){
        if(orderService.sendJsonToClient(params)){
            return "success";
        } else {
            return "error";
        }
    }
    
}
