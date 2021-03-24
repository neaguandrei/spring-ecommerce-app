package aneagu.proj.service;

import aneagu.proj.models.entity.*;
import aneagu.proj.models.dto.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MapperService {

    private final ModelMapper modelMapper;

    public MapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
    }


    public OrderDetailsWrapperDto fromOrderDetailsToFullDetailsDto(List<OrderDetailsEntity> list) {
        OrderDetailsWrapperDto orderDetailsWrapperDto = new OrderDetailsWrapperDto();
        orderDetailsWrapperDto.setProducts(new ArrayList<>());
        Map<Long, OrderDetailsSpecificsDto> specificsMap = new HashMap<>();

        OrderDetailsEntity firstOrderDetailsEntity = list.get(0);
        Optional.of(firstOrderDetailsEntity.getId())
                .ifPresent(orderProductId -> orderDetailsWrapperDto.setId(convertOrderProductIdToOrderProductIdDto(orderProductId)));
        Optional.of(firstOrderDetailsEntity.getOrder())
                .ifPresent(order -> orderDetailsWrapperDto.setOrder(convertOrderToOrderDto(order)));

        for (OrderDetailsEntity orderDetailsEntity : list) {
            OrderDetailsSpecificsDto orderDetailsSpecificsDto = new OrderDetailsSpecificsDto();
            Optional.of(firstOrderDetailsEntity.getPriceEach())
                    .ifPresent(orderDetailsSpecificsDto::setPriceEach);
            Optional.of(firstOrderDetailsEntity.getQuantity())
                    .ifPresent(orderDetailsSpecificsDto::setQuantity);
            specificsMap.put(orderDetailsEntity.getProduct().getId(), orderDetailsSpecificsDto);
            orderDetailsWrapperDto.getProducts().add(convertProductToProductDto(orderDetailsEntity.getProduct()));
        }
        orderDetailsWrapperDto.setSpecifics(specificsMap);

        return orderDetailsWrapperDto;
    }

    public AddressEntity convertAddressDtoToAddress(AddressDto addressDto) {
        return modelMapper.map(addressDto, AddressEntity.class);
    }

    public AddressDto convertAddressToAddressDto(AddressEntity addressEntity) {
        return modelMapper.map(addressEntity, AddressDto.class);
    }

    public UserEntity convertCustomerDtoToCustomer(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);

    }

    public UserDto convertCustomerToCustomerDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    public OrderDetailsEntity convertOrderDetailsDtoToOrderDetails(OrderDetailsDto orderDetailsDto) {
        return modelMapper.map(orderDetailsDto, OrderDetailsEntity.class);

    }

    public OrderDetailsDto convertOrderDetailsToOrderDetailsDto(OrderDetailsEntity orderDetailsEntity) {
        return modelMapper.map(orderDetailsEntity, OrderDetailsDto.class);

    }

    public OrderDetailsId convertOrderProductIdDtoToOrderProductId(OrderDetailsIdDto orderDetailsDto) {
        return modelMapper.map(orderDetailsDto, OrderDetailsId.class);
    }

    public OrderDetailsIdDto convertOrderProductIdToOrderProductIdDto(OrderDetailsId orderDetailsDto) {
        return modelMapper.map(orderDetailsDto, OrderDetailsIdDto.class);
    }

    public OrderEntity convertOrderDtoToOrder(OrderDto orderDto) {
        return modelMapper.map(orderDto, OrderEntity.class);
    }

    public OrderDto convertOrderToOrderDto(OrderEntity orderEntity) {
        return modelMapper.map(orderEntity, OrderDto.class);
    }

    public PaymentEntity convertPaymentDtoToPayment(PaymentDto paymentDto) {
        return modelMapper.map(paymentDto, PaymentEntity.class);
    }

    public PaymentDto convertPaymentToPaymentDto(PaymentEntity paymentEntity) {
        return modelMapper.map(paymentEntity, PaymentDto.class);
    }

    public ProductEntity convertProductDtoToProduct(ProductDto productDto) {
        return modelMapper.map(productDto, ProductEntity.class);
    }

    public ProductDto convertProductToProductDto(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDto.class);
    }

}
