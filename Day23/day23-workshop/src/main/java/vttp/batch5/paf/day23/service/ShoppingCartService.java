package vttp.batch5.paf.day23.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import vttp.batch5.paf.day23.repo.ShoppingCartRepo;

@Service
public class ShoppingCartService {
    
    @Autowired
    private ShoppingCartRepo shoppingCartRepo;

    public boolean addData(int checkoutId, JsonObject jsonObject){
        boolean isCheckoutAdded = shoppingCartRepo.addCheckout(checkoutId, jsonObject.getString("name"), 
        jsonObject.getString("deliveryDate"));

        boolean isPersonAdded = shoppingCartRepo.addPerson(jsonObject.getString("name"), 
        jsonObject.getString("address"), checkoutId);

        JsonArray itemArray = jsonObject.getJsonArray("lineItems");
        boolean isCartAdded = false;
        for(int i = 0; i < itemArray.size(); i++){
            JsonObject item = itemArray.getJsonObject(i);
            isCartAdded = shoppingCartRepo.addItemToCart(item.getString("name"), item.getInt("quantity"), 
            item.getJsonNumber("unitPrice").doubleValue(), checkoutId);
        }

        return isCheckoutAdded && isPersonAdded && isCartAdded;
    }
}
