package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@Setter
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ItemTm extends RecursiveTreeObject<ItemTm> {
    public ItemTm(String code, String description, Double unitPrice, int qty) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    private String code;
    private String description;
    private Double unitPrice;
    private int qty;
    private JFXButton button;
}
