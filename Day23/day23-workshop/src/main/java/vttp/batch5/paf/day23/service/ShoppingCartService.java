package vttp.batch5.paf.day23.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.batch5.paf.day23.repo.ShoppingCartRepo;

@Service
public class ShoppingCartService {
    
    @Autowired
    ShoppingCartRepo shoppingCartRepo;

    public boolean addCheckout(int checkoutId, String name, String deliveryDate){
        return shoppingCartRepo.addCheckout(checkoutId, name, deliveryDate);
    }

    public boolean addPerson(String name, String address, int checkoutId){
        return shoppingCartRepo.addPerson(name, address, checkoutId);
        // && shoppingCartRepo.updatePersonCheckOutId(name, checkoutId)
    }

    public boolean addItemToCart(String name, int quantity, double unitPrice, int checkoutId){
        return shoppingCartRepo.addItemToCart(name, quantity, unitPrice, checkoutId); 
        // && shoppingCartRepo.updateCartCheckOutId(name, checkoutId);
    }
}
