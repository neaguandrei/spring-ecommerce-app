package aneagu.proj.service;


import aneagu.proj.models.domain.*;
import aneagu.proj.models.dto.*;

public interface MapperService {

    Address convertAddressDtoToAddress(AddressDto addressDto);

    AddressDto convertAddressToAddressDto(Address address);

    Customer convertCustomerDtoToCustomer(CustomerDto customerDto);

    CustomerDto convertCustomerToCustomerDto(Customer customer);

    OrderProduct convertOrderProductDtoToOrderProduct(OrderProductDto orderProductDto);

    OrderProductDto convertOrderProductToOrderProductDto(OrderProduct orderProduct);

    Order convertOrderDtoToOrder(OrderDto orderDto);

    OrderDto convertOrderToOrderDto(Order order);

    Payment convertOrderDtoToOrder(PaymentDto paymentDto);

    PaymentDto convertOrderToOrderDto(Payment payment);

    Product convertProductDtoToProduct(ProductDto productDto);

    ProductDto convertProductToProductDto(Product product);

    ProductLine convertProductLineDtoToProductLine(ProductLineDto productLineDto);

    ProductLineDto convertProductLineToProductLineDto(ProductLine productLine);
}
