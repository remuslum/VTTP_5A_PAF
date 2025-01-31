package vttp.batch5.paf.day27.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.batch5.paf.day27.models.LineItem;
import vttp.batch5.paf.day27.models.PurchaseOrder;
import vttp.batch5.paf.day27.repositories.EventRepo;
import vttp.batch5.paf.day27.repositories.MyStoreRepo;
import vttp.batch5.paf.day27.util.Utils;

@Service
public class PurchaseOrderService {

  @Autowired
  private MyStoreRepo myStoreRepo;

  @Autowired
  private EventRepo eventRepo;

  private PurchaseOrder createPurchaseOrder(String payload) {
    String poId = UUID.randomUUID().toString().substring(0, 8);
    PurchaseOrder po = Utils.toPurchaseOrder(payload);
    po.setPoId(poId);
    return po;
  }

  public boolean insertPurchaseOrderAndLineItems(String payload){
    PurchaseOrder purchaseOrder = createPurchaseOrder(payload);
    boolean isPurchaseOrderAdded = myStoreRepo.insertPurchaseOrder(purchaseOrder);
    boolean isItemAdded = true;
    boolean isEventAdded = !eventRepo.insertEvent(purchaseOrder).isEmpty();
    
    for(LineItem item:purchaseOrder.getLineItems()){
      item.setPoId(purchaseOrder.getPoId());
      isItemAdded = myStoreRepo.insertLineItem(item);
      if(!isItemAdded){
        return isItemAdded;
      }
    }
    return isPurchaseOrderAdded && isItemAdded && isEventAdded;
  }

}
