package vttp.batch5.paf.day27.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.paf.day27.models.LineItem;
import vttp.batch5.paf.day27.models.PurchaseOrder;
import static vttp.batch5.paf.day27.util.Fields.C_NAME;
import static vttp.batch5.paf.day27.util.Fields.EVENT_ID_KEY;
import static vttp.batch5.paf.day27.util.Fields.FIELDS_KEY;
import static vttp.batch5.paf.day27.util.Fields.LINEITEM_NAME_KEY;
import static vttp.batch5.paf.day27.util.Fields.LINEITEM_QUANTITY_KEY;
import static vttp.batch5.paf.day27.util.Fields.LINEITEM_TABLE_KEY;
import static vttp.batch5.paf.day27.util.Fields.LINEITEM_TABLE_NAME;
import static vttp.batch5.paf.day27.util.Fields.LINEITEM_UNIT_PRICE_KEY;
import static vttp.batch5.paf.day27.util.Fields.OPERATIONS_ADD;
import static vttp.batch5.paf.day27.util.Fields.OPERATIONS_KEY;
import static vttp.batch5.paf.day27.util.Fields.PURCHASE_ORDER_ADDRESS_KEY;
import static vttp.batch5.paf.day27.util.Fields.PURCHASE_ORDER_DELIVERYDATE_KEY;
import static vttp.batch5.paf.day27.util.Fields.PURCHASE_ORDER_LINEITEMS_KEY;
import static vttp.batch5.paf.day27.util.Fields.PURCHASE_ORDER_NAME_KEY;
import static vttp.batch5.paf.day27.util.Fields.PURCHASE_ORDER_TABLE_NAME;
import static vttp.batch5.paf.day27.util.Fields.TABLE_KEY;
import static vttp.batch5.paf.day27.util.Fields.TIMESTAMP_KEY;

@Repository
public class EventRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Document insertEvent(PurchaseOrder purchaseOrder){
        Document fields = purchaseOrderFields(purchaseOrder);
        Document event = new Document();

        event.append(EVENT_ID_KEY,purchaseOrder.getPoId()).append(TIMESTAMP_KEY, new Date())
        .append(FIELDS_KEY,fields).append(OPERATIONS_KEY, OPERATIONS_ADD)
        .append(TABLE_KEY, PURCHASE_ORDER_TABLE_NAME);

        return mongoTemplate.insert(event, C_NAME);
    }

    private Document purchaseOrderFields(PurchaseOrder purchaseOrder){
        List<Document> lineItems = new ArrayList<>();
        purchaseOrder.getLineItems().forEach(item -> lineItems.add(lineItemFields(item)));

        Document document = new Document();
        document.append(PURCHASE_ORDER_NAME_KEY, purchaseOrder.getName())
        .append(PURCHASE_ORDER_ADDRESS_KEY, purchaseOrder.getAddress())
        .append(PURCHASE_ORDER_DELIVERYDATE_KEY, purchaseOrder.getDeliveryDate())
        .append(PURCHASE_ORDER_LINEITEMS_KEY,lineItems);
        
        return document;
    }

    private Document lineItemFields(LineItem lineItem){
        Document document = new Document();
        document.append(LINEITEM_NAME_KEY, lineItem.getName()).append(LINEITEM_QUANTITY_KEY, lineItem.getQuantity())
        .append(LINEITEM_UNIT_PRICE_KEY, lineItem.getUnitPrice())
        .append(LINEITEM_TABLE_KEY, LINEITEM_TABLE_NAME);
        return document;
    }

    
}
