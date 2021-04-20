package com.fmi.user.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;

    private String email;

    private String password;

    private String oldPassword;

    private String firstName;

    private String lastName;

    private String phone;

    private Address address;

    private List<String> roles;

}
