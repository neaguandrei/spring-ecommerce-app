package com.fmi.api.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AddressDto {

    private Long id;

    @NotNull(message = "Address can't be empty.")
    @Size(min = 3, max = 20)
    private String addressOne;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(max = 20)
    private String addressTwo;

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
