package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private int id;
    private String product;
    private float unitPrice;
    private float discount;
    private int quantity;

}
