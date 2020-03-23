package aneagu.proj.models.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {

    private Long id;

    @NotEmpty(message = "Email can't be empty.")
    private String email;

    @NotEmpty(message = "First name can't be empty.")
    @Size(min = 3, max = 15)
    private String firstName;

    @NotEmpty(message = "Last name can't be empty.")
    @Size(min = 3, max = 15)
    private String lastName;

    @NotEmpty(message = "Phone can't be empty.")
    @Size(min = 3, max = 15)
    private String phone;

    @NotEmpty(message = "Please insert the password")
    @Size(min = 6, max = 15, message = "Keep it between 6 and 15 characters!")
    private String password;

    private AddressDto address;
}
