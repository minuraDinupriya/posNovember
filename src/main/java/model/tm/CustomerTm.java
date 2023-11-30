package model.tm;

import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CustomerTm {

    private String id;
    private String name;
    private String address;
    private Double salary;
    private Button optionBtn;

}
