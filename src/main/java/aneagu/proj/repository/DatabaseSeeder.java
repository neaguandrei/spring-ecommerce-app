package aneagu.proj.repository;

import aneagu.proj.models.domain.*;
import aneagu.proj.models.enums.PaymentMethod;
import aneagu.proj.models.enums.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationListener<ApplicationReadyEvent> {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final OrderDetailsRepository orderDetailsRepository;

    private final PaymentRepository paymentRepository;

    private final ProductRepository productRepository;

    private final ProductLineRepository productLineRepository;

    private static final String DUMMY_PASSWORD = "parola";

    private final PasswordEncoder passwordEncoder;

    @Value(value = "${database-seeder.populate:}")
    private Boolean isSeedingEnabled;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (isSeedingEnabled != null && isSeedingEnabled) {
            List<ProductLine> productLines = seedProductLines();
            List<Product> products = seedProducts(productLines);
            List<User> users = seedCustomersAndAddresses();
            seedPayments(users);
            seedOrderAndOrderDetails(products, users);
        }
    }

    private void seedPayments(List<User> users) {
        long id = 1L;
        for (int i = 0; i < users.size(); i++) {
            paymentRepository.save(new Payment(id++, new Date(), generateLong(200L, 1020), PaymentMethod.values()[i], users.get(i)));
        }
    }

    private List<User> seedCustomersAndAddresses() {
        long id = 1;
        List<User> list = new ArrayList<>();

        String encodedPassword = passwordEncoder.encode(DUMMY_PASSWORD);

        Address address = new Address(id, "Str. X", "asd",
                "Bucharest", "Bucharest", "Romania", "021996");
        addressRepository.save(address);
        User user = new User(id++, "andreineagu.c@gmail.com", encodedPassword, "Andrei",
                "Neagu", "0723111927", address, Collections.emptySet());
        userRepository.save(user);

        Address address2 = new Address(id, "Str. Y", "dsa",
                "Bucharest", "Bucharest", "Romania", "21323");
        addressRepository.save(address2);
        User user2 = new User(id++, "irisneagu@gmail.com", encodedPassword, "Iris",
                "Neagu", "0723111928", address2, Collections.emptySet());
        userRepository.save(user2);

        Address address3 = new Address(id, "Str. Z", "dsa",
                "Bucharest", "Bucharest", "Romania", "21323");
        addressRepository.save(address3);
        User user3 = new User(id++, "claudianeamtu@gmail.com", encodedPassword, "Claudia",
                "Neamtu", "0723111929", address3, Collections.emptySet());
        userRepository.save(user3);

        list.add(user);
        list.add(user2);
        list.add(user3);

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

    private void seedOrderAndOrderDetails(List<Product> products, List<User> users) {
        List<Order> orders = new ArrayList<>();
        orders.add(orderRepository.save(new Order(1L, new Date(), "Deliver it at noon please!",
                users.get(0), Collections.emptySet())));
        orders.add(orderRepository.save(new Order(2L, new Date(), "Deliver it in the morning please!",
                users.get(1), Collections.emptySet())));
        orders.add(orderRepository.save(new Order(3L, new Date(), "Call before delivery!",
                users.get(2), Collections.emptySet())));

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