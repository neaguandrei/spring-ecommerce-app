package aneagu.proj.service;

import aneagu.proj.models.dto.*;
import aneagu.proj.models.exception.NotFoundException;

import java.util.List;
import java.util.Map;

public interface OrderDetailsService {

    Long save(OrderDto orderDto, List<ProductDto> productDtos,
              Map<Long, OrderDetailsSpecificsDto> specificsMap)  throws NotFoundException;

    OrderDetailsWrapperDto get(Long orderId) throws NotFoundException;
}
