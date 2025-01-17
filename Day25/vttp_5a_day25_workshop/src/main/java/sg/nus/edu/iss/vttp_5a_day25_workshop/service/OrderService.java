package sg.nus.edu.iss.vttp_5a_day25_workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import sg.nus.edu.iss.vttp_5a_day25_workshop.model.Order;
import sg.nus.edu.iss.vttp_5a_day25_workshop.model.OrderDetails;
import sg.nus.edu.iss.vttp_5a_day25_workshop.repo.OrderRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;
    
    public boolean sendJsonToClient(MultiValueMap<String, String> params){
        return orderRepo.sendJsonToClient(Order.createOrder(params), OrderDetails.createOrderDetails(params));
    }
}
