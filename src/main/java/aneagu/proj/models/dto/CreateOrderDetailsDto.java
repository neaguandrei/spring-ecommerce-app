package aneagu.proj.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@Data
public class CreateOrderDetailsDto {

    @JsonProperty(value = "order")
    private OrderDto orderDto;

    @NotEmpty
    @JsonProperty(value = "products")
    private List<ProductDto> productDtosList;

    @NotEmpty
    @JsonProperty(value = "specifics")
    private Map<Long, OrderDetailsSpecificsDto> orderDetailSpecifics;
}
