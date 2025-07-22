package com.progmatic.store.account.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.progmatic.store.account")
@EnableJpaRepositories(basePackages = "com.progmatic.store.account.repository")
@EntityScan(basePackages = "com.progmatic.store.account.entity")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
