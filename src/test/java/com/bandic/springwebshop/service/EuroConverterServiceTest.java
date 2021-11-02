package com.bandic.springwebshop.service;

import com.bandic.springwebshop.exception.HnbExchangeException;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EuroConverterServiceTest {
    private static final String URL_SUFFIX = "/tecajn/v1?valuta=EUR";

    @RegisterExtension
    static WireMockExtension wm1 = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8777))
            .configureStaticDsl(true)
            .build();

    private final EuroConverterService euroConverterService =
            new EuroConverterService(WebClient.create("http://localhost:8777/tecajn/v1?valuta=EUR"));

    @Test
    public void whenValidResponseExchangeRateIsConvertedAsExpected() {
        String validJson = """
                        [
                          {
                            "Broj tečajnice": "211",
                            "Datum primjene": "02.11.2021",
                            "Država": "EMU",
                            "Šifra valute": "978",
                            "Valuta": "EUR",
                            "Jedinica": 1,
                            "Kupovni za devize": "7,486630",
                            "Srednji za devize": "7,509123",
                            "Prodajni za devize": "7,531684"
                          }
                        ]
                """;
        stubForParams(200, validJson);

        assertEquals(euroConverterService.getEuroExchangeRate(), BigDecimal.valueOf(7.509123));
    }

    @Test
    public void whenInvalidResponseErrorIsThrown() {
        String json = "Bla";
        stubForParams(200, json);

        assertThrows(HnbExchangeException.class, euroConverterService::getEuroExchangeRate);
    }

    @Test
    public void whenErrorResponseErrorIsThrown() {
        String json = "{\"something\": 100}";
        stubForParams(404, json);

        assertThrows(HnbExchangeException.class, euroConverterService::getEuroExchangeRate);
    }

    private void stubForParams(int httpCode, String json) {
        stubFor(get(urlEqualTo(URL_SUFFIX))
                .willReturn(aResponse()
                        .withStatus(httpCode)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(json)));
    }
}
