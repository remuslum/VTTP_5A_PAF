package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model;

import java.sql.Date;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int orderId;
    private Date orderDate;

    @NotNull(message = "Name cannot be empty")
    @Size(min = 2, message = "Customer name cannot be shorter than 2 characters")
    private String customerName;

    @NotNull(message = "Shipping Address cannot be empty")
    @Size(min = 5, message = "Shipping address cannot be shorter than 5 characters")
    private String shipAddress;

    @NotNull(message = "Notes cannot be empty")
    @Size(min = 3, message = "Notes cannot be shorter than 5 characters")
    private String notes;

    @NotNull(message = "Tax cannot be empty")
    @DecimalMin(value = "0.0", message = "Tax cannot be negative")
    @DecimalMax(value = "1.0", message = "Tax cannot be higher than 1")
    private float tax;
}
