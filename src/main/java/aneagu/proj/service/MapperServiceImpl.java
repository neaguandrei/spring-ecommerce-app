package aneagu.proj.service;

import aneagu.proj.models.domain.*;
import aneagu.proj.models.dto.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MapperServiceImpl implements MapperService {

    private final ModelMapper modelMapper;

    public MapperServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
    }

    @Override
    public OrderDetailsWrapperDto fromOrderDetailsToFullDetailsDto(List<OrderDetails> list) {
        OrderDetailsWrapperDto orderDetailsWrapperDto = new OrderDetailsWrapperDto();
        orderDetailsWrapperDto.setProducts(new ArrayList<>());
        Map<Long, OrderDetailsSpecificsDto> specificsMap = new HashMap<>();

        OrderDetails firstOrderDetails = list.get(0);
        Optional.of(firstOrderDetails.getId())
                .ifPresent(orderProductId -> {
                    orderDetailsWrapperDto.setId(convertOrderProductIdToOrderProductIdDto(orderProductId));
                });
        Optional.of(firstOrderDetails.getOrder())
                .ifPresent(order -> {
                    orderDetailsWrapperDto.setOrder(convertOrderToOrderDto(order));
                });

        for (OrderDetails orderDetails : list) {
            OrderDetailsSpecificsDto orderDetailsSpecificsDto = new OrderDetailsSpecificsDto();
            Optional.of(firstOrderDetails.getPriceEach())
                    .ifPresent(orderDetailsSpecificsDto::setPriceEach);
            Optional.of(firstOrderDetails.getQuantity())
                    .ifPresent(orderDetailsSpecificsDto::setQuantity);
            specificsMap.put(orderDetails.getProduct().getId(), orderDetailsSpecificsDto);
            orderDetailsWrapperDto.getProducts().add(convertProductToProductDto(orderDetails.getProduct()));
        }
        orderDetailsWrapperDto.setSpecifics(specificsMap);

        return orderDetailsWrapperDto;
    }

    @Override
    public Address convertAddressDtoToAddress(AddressDto addressDto) {
        return modelMapper.map(addressDto, Address.class);
    }

    @Override
    public AddressDto convertAddressToAddressDto(Address address) {
        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public User convertCustomerDtoToCustomer(UserDto userDto) {
        return modelMapper.map(userDto, User.class);

    }

    @Override
    public UserDto convertCustomerToCustomerDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public OrderDetails convertOrderDetailsDtoToOrderDetails(OrderDetailsDto orderDetailsDto) {
        return modelMapper.map(orderDetailsDto, OrderDetails.class);

    }

    @Override
    public OrderDetailsDto convertOrderDetailsToOrderDetailsDto(OrderDetails orderDetails) {
        return modelMapper.map(orderDetails, OrderDetailsDto.class);

    }

    @Override
    public OrderDetailsId convertOrderProductIdDtoToOrderProductId(OrderDetailsIdDto orderDetailsDto) {
        return modelMapper.map(orderDetailsDto, OrderDetailsId.class);
    }

    @Override
    public OrderDetailsIdDto convertOrderProductIdToOrderProductIdDto(OrderDetailsId orderDetailsDto) {
        return modelMapper.map(orderDetailsDto, OrderDetailsIdDto.class);
    }

    @Override
    public Order convertOrderDtoToOrder(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    @Override
    public OrderDto convertOrderToOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public Payment convertPaymentDtoToPayment(PaymentDto paymentDto) {
        return modelMapper.map(paymentDto, Payment.class);
    }

    @Override
    public PaymentDto convertPaymentToPaymentDto(Payment payment) {
        return modelMapper.map(payment, PaymentDto.class);
    }

    @Override
    public Product convertProductDtoToProduct(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

    @Override
    public ProductDto convertProductToProductDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductLine convertProductLineDtoToProductLine(ProductLineDto productLineDto) {
        return modelMapper.map(productLineDto, ProductLine.class);
    }

    @Override
    public ProductLineDto convertProductLineToProductLineDto(ProductLine productLine) {
        return modelMapper.map(productLine, ProductLineDto.class);
    }


}
