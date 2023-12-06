package dto.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter

public class OrderTm extends RecursiveTreeObject<OrderTm> {
    private String code;
    private String description;
    private int qty;
    private double amount;
    private Button button;
}
