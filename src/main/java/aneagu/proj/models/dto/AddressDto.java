package aneagu.proj.models.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class)
public class AddressDto {

    private Long id;

    @NotNull(message = "Address can't be empty.")
    @Size(min = 3, max = 20)
    private String addressLineOne;

    @Size(max = 20)
    private String addressLineTwo;

    @NotNull(message = "City can't be empty.")
    @Size(min = 3, max = 15)
    private String city;

    @NotNull(message = "State can't be empty.")
    @Size(min = 3, max = 15)
    private String state;

    @NotNull(message = "Country can't be empty.")
    @Size(min = 3, max = 15)
    private String country;

    @NotNull(message = "Postal code can't be empty.")
    @Size(min = 3, max = 7)
    private String postalCode;
}
