package vttp.batch5.paf.day23.controller;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpSession;
import vttp.batch5.paf.day23.service.ShoppingCartService;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    
    @PutMapping("/purchaseorder")
    public ResponseEntity<String> checkCartOut(@RequestBody String cart, HttpSession httpSession){
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

        JsonObject jsonObject = Json.createReader(new StringReader(cart)).readObject();
        boolean isCheckoutAdded = shoppingCartService.addCheckout(cartIdInt, jsonObject.getString("name"), 
        jsonObject.getString("deliveryDate"));
        
        boolean isPersonAdded = shoppingCartService.addPerson(jsonObject.getString("name"), 
        jsonObject.getString("address"), cartIdInt);
        
        JsonArray itemArray = jsonObject.getJsonArray("lineItems");
        boolean isCartAdded = false;
        for(int i = 0; i < itemArray.size(); i++){
            JsonObject item = itemArray.getJsonObject(i);
            isCartAdded = shoppingCartService.addItemToCart(item.getString("name"), item.getInt("quantity"), 
            item.getJsonNumber("unitPrice").doubleValue(), cartIdInt);
        }
        
        
        if(isPersonAdded && isCartAdded && isCheckoutAdded){
            return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("Successfully Added");
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Unsuccessful");
        }
    }
}
