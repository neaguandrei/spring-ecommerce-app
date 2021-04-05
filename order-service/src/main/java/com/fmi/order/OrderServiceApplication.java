package com.fmi.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.fmi.common",
        "com.fmi.order",
        "com.fmi.dao",
        "com.fmi.security",
        "com.fmi.api"
})
@EnableJpaRepositories(basePackages = "com.fmi.dao.repository")
@EntityScan(basePackages = "com.fmi.dao.entity")
@EnableConfigurationProperties
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
