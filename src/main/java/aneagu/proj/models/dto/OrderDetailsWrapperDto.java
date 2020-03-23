package aneagu.proj.models.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrderDetailsWrapperDto {

    private OrderDetailsIdDto id;

    private OrderDto order;

    private List<ProductDto> products;

    private Map<Long, OrderDetailsSpecificsDto> specifics;

}
