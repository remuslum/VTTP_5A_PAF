package vttp.batch5.paf.day27.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import vttp.batch5.paf.day27.services.PurchaseOrderService;

@Controller
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PurchaseOrderController {

  @Autowired
  private PurchaseOrderService poSvc;

  @PostMapping(path="/purchaseorder", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<String> postPurchaseOrder(@RequestBody String payload) {
    boolean isOrderAdded = poSvc.insertPurchaseOrderAndLineItems(payload);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    if(isOrderAdded){
      objectBuilder.add("message", "Order successfully added");
      return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(objectBuilder.build().toString());
    } else {
      objectBuilder.add("message", "Unsuccessful, please try again");
      return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(objectBuilder.build().toString());
    }

    // Returns the poId as JSON object
    // JsonObject resp = Json.createObjectBuilder()
    //     .add("poId", po.getPoId())
    //     .build();
    // return ResponseEntity.ok(resp.toString());
  }

}
