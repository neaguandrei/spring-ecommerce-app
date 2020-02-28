package aneagu.proj.models.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class)
public class CustomerDto {

    private Long id;

    @NotNull(message = "Email can't be empty.")
    private String email;

    @NotNull(message = "Name can't be empty.")
    @Size(min = 3, max = 15)
    private String firstName;

    @NotNull(message = "Name can't be empty.")
    @Size(min = 3, max = 15)
    private String lastName;

    @NotNull(message = "Phone can't be empty.")
    @Size(min = 3, max = 15)
    private String phone;

    private AddressDto addressDto;

    private Set<PaymentDto> paymentDtos;
}
