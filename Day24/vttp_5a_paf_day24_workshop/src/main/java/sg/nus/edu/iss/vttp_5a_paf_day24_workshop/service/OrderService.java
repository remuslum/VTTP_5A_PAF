package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.Order;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model.OrderDetails;
import sg.nus.edu.iss.vttp_5a_paf_day24_workshop.repo.OrderRepo;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepo orderRepo;

    public boolean insertDetails(Order order, OrderDetails orderDetails){
        return orderRepo.addOrderAndOrderDetails(order, orderDetails);
    }
}
