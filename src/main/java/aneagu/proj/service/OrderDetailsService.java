package aneagu.proj.service;

import aneagu.proj.models.entity.OrderEntity;
import aneagu.proj.models.entity.OrderDetailsEntity;
import aneagu.proj.models.entity.OrderDetailsId;
import aneagu.proj.models.entity.ProductEntity;
import aneagu.proj.models.dto.*;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.repository.OrderDetailsRepository;
import aneagu.proj.repository.OrderRepository;
import aneagu.proj.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.util.Lists;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final MapperService mapperService;

    public Long save(OrderDto orderDto, List<ProductDto> products,
                     Map<Long, OrderDetailsSpecificsDto> specificsMap) throws NotFoundException {
        OrderEntity orderEntity = orderRepository.save(mapperService.convertOrderDtoToOrder(orderDto));
        for (ProductDto productDto : products) {
            ProductEntity productEntity = getProductIfExisting(productDto);
            OrderDetailsSpecificsDto specifics = specificsMap.get(productEntity.getId());
            OrderDetailsEntity orderDetailsEntity = buildOrderDetails(orderEntity, productEntity, specifics);
            orderDetailsRepository.save(orderDetailsEntity);
        }

        return orderEntity.getId();
    }

    public OrderDetailsWrapperDto get(Long orderId) throws NotFoundException {
        List<OrderDetailsEntity> list = Lists.from(orderDetailsRepository.findByOrder_Id(orderId).iterator());
        if (list.isEmpty()) {
            throw new NotFoundException("Order Details not found!");
        }

        return mapperService.fromOrderDetailsToFullDetailsDto(list);
    }

    private ProductEntity getProductIfExisting(ProductDto productDto) throws NotFoundException {
        if (!productRepository.findById(productDto.getId()).isPresent()) {
            throw new NotFoundException("Product doesn't exist in the database!");
        }
        return mapperService.convertProductDtoToProduct(productDto);
    }

    private OrderDetailsEntity buildOrderDetails(OrderEntity orderEntity, ProductEntity productEntity, OrderDetailsSpecificsDto specifics) {
        return OrderDetailsEntity.builder()
                .id(new OrderDetailsId(orderEntity.getId(), productEntity.getId()))
                .priceEach(specifics.getPriceEach())
                .quantity(specifics.getQuantity())
                .order(orderEntity)
                .product(productEntity)
                .build();
    }
}
