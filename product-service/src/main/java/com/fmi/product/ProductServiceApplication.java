package com.fmi.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.fmi.common",
        "com.fmi.product",
        "com.fmi.dao",
        "com.fmi.security",
        "com.fmi.api.product"
})
@EnableJpaRepositories(basePackages = "com.fmi.dao.repository")
@EntityScan(basePackages = "com.fmi.dao.entity")
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
