package aneagu.proj.models.dto;

import aneagu.proj.models.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class)
public class PaymentDto {

    private Long id;

    @NotNull(message = "Date can't be empty.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    @NotNull(message = "Amount can't be empty.")
    private Long amount;

    @NotNull(message = "Payment method can't be empty.")
    private PaymentMethod paymentMethod;

    private CustomerDto customer;

}
