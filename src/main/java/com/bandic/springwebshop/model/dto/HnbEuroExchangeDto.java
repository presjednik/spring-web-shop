package com.bandic.springwebshop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HnbEuroExchangeDto {
    @JsonProperty("Broj tečajnice")
    private String exchangeNumber;
    @JsonProperty("Datum primjene")
    private String dateOfApplication;
    @JsonProperty("Država")
    private String country;
    @JsonProperty("Šifra valute")
    private String currencyCode;
    @JsonProperty("Valuta")
    private String currency;
    @JsonProperty("Jedinica")
    private String quantity;
    @JsonProperty("Kupovni za devize")
    private String purchaseExchangeRate;
    @JsonProperty("Srednji za devize")
    private String middleExchangeRate;
    @JsonProperty("Prodajni za devize")
    private String sellingExchangeRate;
}
