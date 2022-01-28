package uz.pdp.lesson1task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {

    @NotNull(message = "name should not be empty")
    private String name;

    @NotNull(message = "phoneNumber should not be empty")
    private String phoneNumber;

    @NotNull(message = "departmentId should not be empty")
    private Integer departmentId;

    @NotNull(message = "street should not be empty")
    private String street;

    @NotNull(message = "homeNumber should not be empty")
    private Integer homeNumber;

}
