package sg.nus.edu.iss.vttp_5a_paf_day25_lecture_consumer.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private String fullName;
    private List<OrderDetail> lineItem;
}
