package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.Order;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.OrderDetails;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.service.OrderService;

@Controller
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public ModelAndView getOrderPage(){
        ModelAndView mav = new ModelAndView("order");
        mav.addObject("order", new Order());
        mav.addObject("orderdetails1", new OrderDetails());
        mav.addObject("orderdetails2", new OrderDetails());
        return mav;
    }

    @PostMapping("/submit")
    public ModelAndView submitOrder(@Valid @ModelAttribute Order order, BindingResult orderBindingResult, 
    @Valid @ModelAttribute OrderDetails orderDetails1, BindingResult orderDetails1BindingResult,
    @Valid @ModelAttribute OrderDetails orderDetails2, BindingResult orderDetails2BindingResult){
        ModelAndView mav = new ModelAndView();
        if(orderBindingResult.hasErrors() || orderDetails1BindingResult.hasErrors() || orderDetails2BindingResult.hasErrors()){
            mav.addObject("order", order);
            mav.addObject("orderdetails1", orderDetails1);
            mav.addObject("orderdetails2", orderDetails2);
            mav.setViewName("order");
        } else {
            mav.setViewName("success");
            orderService.insertDetails(order, orderDetails1);
        }
        
        return mav;
    }
}
