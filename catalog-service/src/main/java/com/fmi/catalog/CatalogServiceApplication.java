package com.fmi.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.fmi.catalog",
        "com.fmi.common",
        "com.fmi.security",
        "com.fmi.api.catalog",
        "com.fmi.api.payment"
})
@EnableJpaRepositories(basePackages = "com.fmi.catalog.dao.repository")
@EntityScan(basePackages = "com.fmi.catalog.dao.entity")
@EnableConfigurationProperties
@EnableEurekaClient
@EnableCaching
public class CatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

}
