package aneagu.proj.repository;

import aneagu.proj.models.entity.*;
import aneagu.proj.models.enums.PaymentMethod;
import aneagu.proj.models.enums.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationListener<ApplicationReadyEvent> {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;

    private final ProductRepository productRepository;

    private static final String DUMMY_PASSWORD = "parola";

    private final PasswordEncoder passwordEncoder;

    @Value(value = "${database-seeder.populate:}")
    private Boolean isSeedingEnabled;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (isSeedingEnabled != null && isSeedingEnabled) {
            List<ProductEntity> productEntities = seedProducts();
            List<UserEntity> userEntities = seedCustomersAndAddresses();
            List<OrderEntity> orderEntities = seedOrders(productEntities, userEntities);
            seedPayments(orderEntities);
        }
    }

    private void seedPayments(List<OrderEntity> orderEntities) {
        long id = 1L;
        for (int i = 0; i < orderEntities.size(); i++) {
            paymentRepository.save(new PaymentEntity(id++, generateBigDecimal(200L, 1020), PaymentMethod.values()[i], orderEntities.get(i)));
        }
    }

    private List<UserEntity> seedCustomersAndAddresses() {
        long id = 1;
        List<UserEntity> list = new ArrayList<>();

        String encodedPassword = passwordEncoder.encode(DUMMY_PASSWORD);

        AddressEntity addressEntity = new AddressEntity(id, "Str. X", "asd",
                "Bucharest", "Bucharest", "Romania", "021996");
        addressRepository.save(addressEntity);
        UserEntity userEntity = new UserEntity(id++, "andreineagu.c@gmail.com", encodedPassword, "Andrei",
                "Neagu", "0723111927", addressEntity);
        userRepository.save(userEntity);

        AddressEntity addressEntity2 = new AddressEntity(id, "Str. Y", "dsa",
                "Bucharest", "Bucharest", "Romania", "21323");
        addressRepository.save(addressEntity2);
        UserEntity userEntity2 = new UserEntity(id++, "irisneagu@gmail.com", encodedPassword, "Iris",
                "Neagu", "0723111928", addressEntity2);
        userRepository.save(userEntity2);

        AddressEntity addressEntity3 = new AddressEntity(id, "Str. Z", "dsa",
                "Bucharest", "Bucharest", "Romania", "21323");
        addressRepository.save(addressEntity3);
        UserEntity userEntity3 = new UserEntity(id++, "claudianeamtu@gmail.com", encodedPassword, "Claudia",
                "Neamtu", "0723111929", addressEntity3);
        userRepository.save(userEntity3);

        list.add(userEntity);
        list.add(userEntity2);
        list.add(userEntity3);

        return list;
    }

    private List<ProductEntity> seedProducts() {
        long id = 1L;
        List<ProductEntity> productEntities = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            productEntities.add(productRepository.save(new ProductEntity(id++, "TV v" + id, "4k", generateLong(5L, 25),
                    generateLong(5L, 25), ProductCategory.MONITORS, "DELL")));
            productEntities.add(productRepository.save(new ProductEntity(id++, "Monitor v" + id, "144Hz", generateLong(5L, 25),
                    generateLong(5L, 25), ProductCategory.MONITORS, "SAMSUNG")));
            productEntities.add(productRepository.save(new ProductEntity(id++, "GPU 1080v" + id, "GPU with 8gb DVM", generateLong(5L, 25),
                    generateLong(5L, 25), ProductCategory.HARDWARE, "GIGABYTE")));
            productEntities.add(productRepository.save(new ProductEntity(id++, "Mouse GPW" + id, "Best mouse on the market",
                    generateLong(5L, 25), generateLong(5L, 25), ProductCategory.PERIPHERALS, "LOGITECH")));
        }

        return productEntities;
    }

    private List<OrderEntity> seedOrders(List<ProductEntity> productEntities, List<UserEntity> userEntities) {
        List<OrderEntity> orderEntities = new ArrayList<>();
        orderEntities.add(orderRepository.save(new OrderEntity(1L, "Deliver it at noon please!",
                userEntities.get(0), productEntities.subList(1, 2))));
        orderEntities.add(orderRepository.save(new OrderEntity(2L, "Deliver it in the morning please!",
                userEntities.get(1), productEntities.subList(1, 3))));
        orderEntities.add(orderRepository.save(new OrderEntity(3L, "Call before delivery!",
                userEntities.get(2), productEntities.subList(2, 4))));
        orderRepository.saveAll(orderEntities);

        return orderEntities;
    }

    private Long generateLong(Long lower, Integer upper) {
        return new Random().nextInt(upper) + lower;
    }

    private BigDecimal generateBigDecimal(Long lower, Integer upper) {
        return BigDecimal.valueOf(new Random().nextInt(upper) + lower);
    }
}