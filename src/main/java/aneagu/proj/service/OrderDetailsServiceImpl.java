package aneagu.proj.service;

import aneagu.proj.models.domain.Order;
import aneagu.proj.models.domain.OrderDetails;
import aneagu.proj.models.domain.OrderDetailsId;
import aneagu.proj.models.domain.Product;
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
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final MapperService mapperService;

    @Override
    public Long save(OrderDto orderDto, List<ProductDto> productDtos,
                     Map<Long, OrderDetailsSpecificsDto> specificsMap) throws NotFoundException {
        Order order = orderRepository.save(mapperService.convertOrderDtoToOrder(orderDto));
        for (ProductDto productDto : productDtos) {
            Product product = getProductIfExisting(productDto);
            OrderDetailsSpecificsDto specifics = specificsMap.get(product.getId());
            OrderDetails orderDetails = buildOrderDetails(order, product, specifics);
            orderDetailsRepository.save(orderDetails);
        }

        return order.getId();
    }

    @Override
    public OrderDetailsWrapperDto get(Long orderId) throws NotFoundException {
        List<OrderDetails> list = Lists.from(orderDetailsRepository.findByOrder_Id(orderId).iterator());
        if (list.isEmpty()) {
            throw new NotFoundException("Order Details not found!");
        }

        return mapperService.fromOrderDetailsToFullDetailsDto(list);
    }

    private Product getProductIfExisting(ProductDto productDto) throws NotFoundException {
        if (!productRepository.findById(productDto.getId()).isPresent()) {
            throw new NotFoundException("Product doesn't exist in the database!");
        }
        return mapperService.convertProductDtoToProduct(productDto);
    }

    private OrderDetails buildOrderDetails(Order order, Product product, OrderDetailsSpecificsDto specifics) {
        return OrderDetails.builder()
                .id(new OrderDetailsId(order.getId(), product.getId()))
                .priceEach(specifics.getPriceEach())
                .quantity(specifics.getQuantity())
                .order(order)
                .product(product)
                .build();
    }
}
