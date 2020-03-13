package aneagu.proj.models.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class OrderDetailsDto {

    private OrderProductIdDto id;

    private ProductDto product;

    private OrderDto order;

    @NotNull(message = "Quantity can't be empty.")
    @Size(min = 1)
    private Long quantity;

    @NotNull(message = "Price per each can't be empty.")
    @Size(min = 1)
    private Long priceEach;
}
