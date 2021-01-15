package com.example.rateexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableFeignClients
@Profile("!test")
public class RateExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateExchangeApplication.class, args);
    }

}
