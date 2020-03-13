package aneagu.proj.service.impl;

import aneagu.proj.models.domain.OrderDetails;
import aneagu.proj.models.dto.OrderDetailsDto;
import aneagu.proj.models.dto.OrderProductIdDto;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.repository.OrderDetailsRepository;
import aneagu.proj.service.MapperService;
import aneagu.proj.service.OrderDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final MapperService mapperService;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository, MapperService mapperService) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.mapperService = mapperService;
    }

    @Override
    public void upsert(OrderDetailsDto orderDetailsDto) {
        Optional<OrderDetails> optionalOrderDetails = orderDetailsRepository
                .findById(mapperService.convertOrderProductIdDtoToOrderProductId(orderDetailsDto.getId()));
        if (optionalOrderDetails.isPresent()) {
            OrderDetails orderDetails = mapperService.convertOrderDetailsDtoToOrderDetails(orderDetailsDto);
            orderDetailsRepository.save(orderDetails);
        }
    }

    @Override
    public OrderDetailsDto get(OrderProductIdDto id) throws NotFoundException {
        Optional<OrderDetails> optionalOrderDetails = orderDetailsRepository
                .findById(mapperService.convertOrderProductIdDtoToOrderProductId(id));
        if (!optionalOrderDetails.isPresent()) {
            throw new NotFoundException("Order Details not found!");
        }

        return mapperService.convertOrderDetailsToOrderDetailsDto(optionalOrderDetails.get());
    }
}
