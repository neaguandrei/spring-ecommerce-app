package aneagu.proj.service;


import aneagu.proj.models.domain.*;
import aneagu.proj.models.dto.*;

import java.util.List;

public interface MapperService {

    OrderDetailsWrapperDto fromOrderDetailsToFullDetailsDto(List<OrderDetails> list);

    Address convertAddressDtoToAddress(AddressDto addressDto);

    AddressDto convertAddressToAddressDto(Address address);

    Customer convertCustomerDtoToCustomer(CustomerDto customerDto);

    CustomerDto convertCustomerToCustomerDto(Customer customer);

    OrderDetails convertOrderDetailsDtoToOrderDetails(OrderDetailsDto orderDetailsDto);

    OrderDetailsDto convertOrderDetailsToOrderDetailsDto(OrderDetails orderDetails);

    OrderDetailsId convertOrderProductIdDtoToOrderProductId(OrderDetailsIdDto orderDetailsDto);

    OrderDetailsIdDto convertOrderProductIdToOrderProductIdDto(OrderDetailsId orderDetailsDto);

    Order convertOrderDtoToOrder(OrderDto orderDto);

    OrderDto convertOrderToOrderDto(Order order);

    Payment convertPaymentDtoToPayment(PaymentDto paymentDto);

    PaymentDto convertPaymentToPaymentDto(Payment payment);

    Product convertProductDtoToProduct(ProductDto productDto);

    ProductDto convertProductToProductDto(Product product);

    ProductLine convertProductLineDtoToProductLine(ProductLineDto productLineDto);

    ProductLineDto convertProductLineToProductLineDto(ProductLine productLine);

}
