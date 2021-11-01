package com.bandic.springwebshop;

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
    public WebClient webClient() {
        return WebClient.create("https://api.hnb.hr/tecajn/v1?valuta=EUR");
    }
}
