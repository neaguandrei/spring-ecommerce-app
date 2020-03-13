package aneagu.proj.service.impl;

import aneagu.proj.models.domain.*;
import aneagu.proj.models.dto.*;
import aneagu.proj.service.MapperService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
public class MapperServiceImpl implements MapperService {

    private final ModelMapper modelMapper;

    public MapperServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
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
    public Customer convertCustomerDtoToCustomer(CustomerDto customerDto) {
        return modelMapper.map(customerDto, Customer.class);

    }

    @Override
    public CustomerDto convertCustomerToCustomerDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
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
    public OrderProductId convertOrderProductIdDtoToOrderProductId(OrderProductIdDto orderDetailsDto) {
        return modelMapper.map(orderDetailsDto, OrderProductId.class);
    }

    @Override
    public OrderProductIdDto convertOrderProductIdToOrderProductIdDto(OrderProductId orderDetailsDto) {
        return modelMapper.map(orderDetailsDto, OrderProductIdDto.class);
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
