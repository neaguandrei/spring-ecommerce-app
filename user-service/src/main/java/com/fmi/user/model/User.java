package com.fmi.user.model;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String email;

    private String password;

    private String oldPassword;

    private String firstName;

    private String lastName;

    private String phone;

    private Address address;

}
