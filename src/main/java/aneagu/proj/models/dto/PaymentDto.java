package aneagu.proj.models.dto;

import aneagu.proj.models.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class PaymentDto {

    private Long id;

    @NotNull(message = "Date can't be empty.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;

    @NotNull(message = "Amount can't be empty.")
    @Size(min = 1, max = 1000)
    private Long amount;

    @NotNull(message = "Payment method can't be empty.")
    private PaymentMethod paymentMethod;

    private Long customerId;

}
