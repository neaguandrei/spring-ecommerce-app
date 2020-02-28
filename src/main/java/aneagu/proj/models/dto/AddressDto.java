package aneagu.proj.models.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class)
public class AddressDto {

    private Long id;

    private String addressLineOne;

    private String addressLineTwo;

    private String city;

    private String state;

    private String country;

    private String postalCode;
}
