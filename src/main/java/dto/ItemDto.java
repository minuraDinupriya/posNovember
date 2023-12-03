package dto;

import lombok.*;

@Setter
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ItemDto {
    private String code;
    private String description;
    private Double unitPrice;
    private int qty;
}
