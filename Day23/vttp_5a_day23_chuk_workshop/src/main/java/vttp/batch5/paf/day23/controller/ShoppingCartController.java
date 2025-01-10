package vttp.batch5.paf.day23.controller;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpSession;
import vttp.batch5.paf.day23.service.ShoppingCartService;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    
    @PutMapping(path="/purchaseorder", produces="application/json")
    public ResponseEntity<Map<String, String>> checkCartOut(@RequestBody String cart, HttpSession httpSession){
        // Check if a cart has already been created, if not initialise cartID
        Optional<Integer> cartId = Optional.ofNullable((Integer) httpSession.getAttribute("cartId"));
        int cartIdInt = cartId
        .map((value) -> {
            httpSession.setAttribute("cartId", value + 1);
            return value + 1;
        })
        .orElseGet(() -> {
            httpSession.setAttribute("cartId", 1);
            return (Integer) httpSession.getAttribute("cartId");
        });
        
        JsonObject cartItems = Json.createReader(new StringReader(cart)).readObject();
        boolean isAdded = shoppingCartService.addData(cartIdInt, cartItems);
        
        Map<String, String> response = new HashMap<>();
        if(isAdded){
            response.put("message", "Successfully added");
            return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(response);
        } else {
            response.put("message", "Unsuccessful");
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(response);
        }
    }
}
