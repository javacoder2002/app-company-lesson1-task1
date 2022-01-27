package uz.pdp.lesson1task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    @NotNull(message = "name should not be empty")
    private String name;

    @NotNull(message = "company should not be empty")
    private Integer companyId;

}
