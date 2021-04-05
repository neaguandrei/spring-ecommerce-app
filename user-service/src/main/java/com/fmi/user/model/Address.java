package com.fmi.user.model;

import lombok.Data;


@Data
public class Address {

    private Long id;

    private String addressOne;

    private String addressTwo;

    private String city;

    private String state;

    private String country;

    private String postalCode;
}
