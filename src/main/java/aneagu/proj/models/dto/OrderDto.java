package aneagu.proj.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class OrderDto {

    private Long id;

    @NotNull(message = "Date can't be empty.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    private String comment;

    private CustomerDto customerDto;

    private Set<OrderDetailsDto> products;
}
