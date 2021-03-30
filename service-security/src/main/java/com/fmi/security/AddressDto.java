package com.fmi.security;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AddressDto {

    private Long id;

    private String addressLineOne;

    private String addressLineTwo;

    private String city;

    private String state;

    private String country;

    private String postalCode;
}
