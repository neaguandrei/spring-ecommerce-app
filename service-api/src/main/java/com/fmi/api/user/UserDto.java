package com.fmi.api.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @NotEmpty(message = "Email can't be empty.", groups = {SignIn.class, SignUp.class})
    private String email;

    @NotEmpty(message = "Please insert the password", groups = {SignIn.class, SignUp.class})
    @Size(min = 6, max = 15, message = "Keep it between 6 and 15 characters!")
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(min = 6, max = 15, message = "Keep it between 6 and 15 characters!")
    private String newPassword;

    @NotEmpty(message = "First name can't be empty.", groups = SignUp.class)
    @Size(min = 3, max = 15)
    private String firstName;

    @NotEmpty(message = "Last name can't be empty.", groups = SignUp.class)
    @Size(min = 3, max = 15)
    private String lastName;

    @NotEmpty(message = "Phone can't be empty.", groups = SignUp.class)
    @Size(min = 3, max = 15)
    private String phone;

    @NotNull(message = "Address can't be empty", groups = {SignUp.class})
    private AddressDto address;

    @NotEmpty(message = "Roles can't be empty", groups = {SignUp.class})
    private List<String> roles;

    public interface SignIn {

    }

    public interface SignUp {

    }
}
