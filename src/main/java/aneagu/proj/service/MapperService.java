package aneagu.proj.service;

import aneagu.proj.models.entity.*;
import aneagu.proj.models.dto.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

    private final ModelMapper modelMapper;

    public MapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
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
