package com.fmi.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.fmi.payment",
        "com.fmi.common",
        "com.fmi.security",
        "com.fmi.api.payment"
})
@EnableJpaRepositories(basePackages = "com.fmi.payment.dao.repository")
@EntityScan(basePackages = "com.fmi.payment.dao.entity")
@EnableConfigurationProperties
@EnableEurekaClient
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

}
