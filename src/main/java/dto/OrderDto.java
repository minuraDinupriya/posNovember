package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class OrderDto {
    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;
}
