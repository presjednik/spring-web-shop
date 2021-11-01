package com.bandic.springwebshop.service;

import com.bandic.springwebshop.exception.HnbExchangeException;
import com.bandic.springwebshop.model.dto.HnbEuroExchangeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class EuroConverterService {
    private final WebClient webClient;

    public BigDecimal getEuroExchangeRate() {
        HnbEuroExchangeDto hnbEuroExchangeDto = webClient.get()
                .retrieve()
                .bodyToFlux(HnbEuroExchangeDto.class)
                .blockFirst(Duration.ofSeconds(3));

        log.info("HNB euro exchange DTO: {}", hnbEuroExchangeDto);
        return stringToBigDecimal(hnbEuroExchangeDto);
    }

    private BigDecimal stringToBigDecimal(HnbEuroExchangeDto dto) {
        try {
            if (dto != null) {
                return new BigDecimal(dto.getMiddleExchangeRate().replace(",", "."));
            }
        } catch (NumberFormatException e) {
            log.error("Couldn't format middle exhange rate from HNB to BigDecimal", e);
        }
        throw new HnbExchangeException();
    }
}
