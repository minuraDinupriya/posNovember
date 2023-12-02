package model.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.control.Button;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ItemTm extends RecursiveTreeObject<ItemTm> {
    public ItemTm(String code, String description, String unitPrice, String qty) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    private String code;
    private String description;
    private String unitPrice;
    private String qty;
    private JFXButton button;
}
