package aneagu.proj.models.dto;

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

    private Long amount;

    private String method;

    private CustomerDto customerDto;

}
