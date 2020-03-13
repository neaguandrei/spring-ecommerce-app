package aneagu.proj.service;

import aneagu.proj.models.dto.OrderDetailsDto;
import aneagu.proj.models.dto.OrderProductIdDto;
import aneagu.proj.models.exception.NotFoundException;

public interface OrderDetailsService {

    void upsert(OrderDetailsDto orderDetailsDto);

    OrderDetailsDto get(OrderProductIdDto id) throws NotFoundException;
}
