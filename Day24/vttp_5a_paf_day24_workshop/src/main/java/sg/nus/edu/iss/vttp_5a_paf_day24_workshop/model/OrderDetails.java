package sg.nus.edu.iss.vttp_5a_paf_day24_workshop.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private int id;
    @NotNull(message = "Product cannot be blank")
    @Size(min = 2, message = "Product must be at least 2 charcters long")
    private String product;

    @NotNull(message = "Unit Price cannot be blank")
    @DecimalMin(value = "0.0", message = "Unit price must be greater than 0")
    private float unitPrice;

    @NotNull(message = "Discount cannot be blank")
    @DecimalMin(value = "0.0", message = "Discount must be greater than 0")
    @DecimalMax(value = "1.0", message = "Discount cannot be above 1")
    private float discount;

    @NotNull(message = "Quantity cannot be blank")
    @Max(value = 0,message = "Quantity must be greater than 0")
    private int quantity;

}
