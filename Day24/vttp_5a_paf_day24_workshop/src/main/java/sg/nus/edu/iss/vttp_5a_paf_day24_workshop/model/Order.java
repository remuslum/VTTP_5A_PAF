package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int orderId;
    private Date orderDate;
    private String customerName;
    private String shipAddress;
    private String notes;
    private float tax;
}
