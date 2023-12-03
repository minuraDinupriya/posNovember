package dto;

import lombok.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@ToString

public class CustomerDto {
    private String id;
    private String name;
    private String address;
    private Double salary;
}
