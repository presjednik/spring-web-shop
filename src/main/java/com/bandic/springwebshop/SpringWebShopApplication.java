package com.bandic.springwebshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SpringWebShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebShopApplication.class, args);
    }

    @Bean
    public WebClient webClient(@Value("${hnb.exchange.rate.url}") String url) {
        return WebClient.create(url);
    }
}
