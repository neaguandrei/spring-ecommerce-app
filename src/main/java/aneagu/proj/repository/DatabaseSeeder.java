package aneagu.proj.repository;

import aneagu.proj.models.domain.*;
import aneagu.proj.models.enums.PaymentMethod;
import aneagu.proj.models.enums.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationListener<ApplicationReadyEvent> {

    private final AddressRepository addressRepository;

    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    private final OrderDetailsRepository orderDetailsRepository;

    private final PaymentRepository paymentRepository;

    private final ProductRepository productRepository;

    private final ProductLineRepository productLineRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<ProductLine> productLines = seedProductLines();
        List<Product> products = seedProducts(productLines);
        List<Customer> customers = seedCustomersAndAddresses();
        seedPayments(customers);
        seedOrderAndOrderDetails(products, customers);
    }

    private void seedPayments(List<Customer> customers) {
        long id = 1L;
        for (int i = 0; i < customers.size(); i++) {
            paymentRepository.save(new Payment(id++, new Date(), generateLong(200L, 1020), PaymentMethod.values()[i], customers.get(i)));
        }
    }

    private List<Customer> seedCustomersAndAddresses() {
        long id = 1;
        List<Customer> list = new ArrayList<>();

        Address address = new Address(id, "Str. X", "asd",
                "Bucharest", "Bucharest", "Romania", "021996");
        addressRepository.save(address);
        Customer customer = new Customer(id++, "andreineagu.c@gmail.com", "password", "Andrei",
                "Neagu", "0723111927", address, Collections.emptySet());
        customerRepository.save(customer);
        Address address2 = new Address(id, "Str. Y", "dsa",
                "Bucharest", "Bucharest", "Romania", "21323");
        addressRepository.save(address2);
        Customer customer2 = new Customer(id++, "irisneagu@gmail.com", "password", "Iris",
                "Neagu", "0723111928", address2, Collections.emptySet());
        customerRepository.save(customer2);

        Address address3 = new Address(id, "Str. Z", "dsa",
                "Bucharest", "Bucharest", "Romania", "21323");
        addressRepository.save(address3);
        Customer customer3 = new Customer(id++, "claudianeamtu@gmail.com", "password", "Claudia",
                "Neamtu", "0723111929", address3, Collections.emptySet());
        customerRepository.save(customer3);

        list.add(customer);
        list.add(customer2);
        list.add(customer3);

        return list;
    }

    private List<ProductLine> seedProductLines() {
        long id = 1L;
        List<ProductLine> productLineList = new ArrayList<>();
        productLineList.add(new ProductLine(id++, "Samsung", "One of best product lines", new byte[10], Collections.emptySet()));
        productLineList.add(new ProductLine(id++, "Dell", "One of best product lines", new byte[10], Collections.emptySet()));
        productLineList.add(new ProductLine(id++, "Nvidia", "Best gaming hardware", new byte[10], Collections.emptySet()));
        productLineList.add(new ProductLine(id++, "Asus", "One of best product lines", new byte[10], Collections.emptySet()));
        return productLineRepository.saveAll(productLineList);
    }

    private List<Product> seedProducts(List<ProductLine> productLines) {
        long id = 1L;
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            products.add(productRepository.save(new Product(id++, "TV v" + id, "4k", generateLong(5L, 25),
                    generateLong(5L, 25), ProductCategory.MONITORS, productLines.get(0), Collections.emptySet())));
            products.add(productRepository.save(new Product(id++, "Monitor v" + id, "144Hz", generateLong(5L, 25),
                    generateLong(5L, 25), ProductCategory.MONITORS, productLines.get(1), Collections.emptySet())));
            products.add(productRepository.save(new Product(id++, "GPU 1080v" + id, "GPU with 8gb DVM", generateLong(5L, 25),
                    generateLong(5L, 25), ProductCategory.HARDWARE, productLines.get(2), Collections.emptySet())));
            products.add(productRepository.save(new Product(id++, "Mouse G905" + id, "Best mouse on the market",
                    generateLong(5L, 25), generateLong(5L, 25), ProductCategory.PERIPHERALS, productLines.get(3), Collections.emptySet())));
        }

        return products;
    }

    private void seedOrderAndOrderDetails(List<Product> products, List<Customer> customers) {
        List<Order> orders = new ArrayList<>();
        orders.add(orderRepository.save(new Order(1L, new Date(), "Deliver it at noon please!",
                customers.get(0), Collections.emptySet())));
        orders.add(orderRepository.save(new Order(2L, new Date(), "Deliver it in the morning please!",
                customers.get(1), Collections.emptySet())));
        orders.add(orderRepository.save(new Order(3L, new Date(), "Call before delivery!",
                customers.get(2), Collections.emptySet())));

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            orderDetailsList.add(new OrderDetails(new OrderDetailsId(orders.get(i).getId(), products.get(i).getId()), products.get(i), orders.get(i),
                    generateLong(1L, 6), generateLong(20L, 45)));
        }
        orderDetailsRepository.saveAll(orderDetailsList);

    }

    private Long generateLong(Long lower, Integer upper) {
        return new Random().nextInt(upper) + lower;
    }
}