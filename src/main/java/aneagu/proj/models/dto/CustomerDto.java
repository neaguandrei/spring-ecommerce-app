package aneagu.proj.models.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class)
public class CustomerDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

    private AddressDto addressDto;

    private Set<PaymentDto> paymentDtos;
}
