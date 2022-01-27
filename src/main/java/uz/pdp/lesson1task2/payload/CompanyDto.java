package uz.pdp.lesson1task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {

    @NotNull(message = "corpName should not be empty")
    private String corpName;

    @NotNull(message = "directorName should not be empty")
    private String directorName;

    @NotNull(message = "street should not be empty")
    private String street;

    @NotNull(message = "homeNumber should not be empty")
    private Integer homeNumber;

}
