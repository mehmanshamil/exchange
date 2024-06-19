package com.abbtech.exchange.service;

import com.abbtech.exchange.model.ExchangeRate;
import com.abbtech.exchange.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateUpdater {

    private final RestTemplate restTemplate;
    private final ExchangeRateRepository repository;
    private static final String API_URL = "https://api.exchangeratesapi.io/latest?base=AZN";

    @Scheduled(fixedRate = 3600000)
    public void updateRates() {
        String response = restTemplate.getForObject(API_URL, String.class);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode ratesNode = root.path("rates");

            Iterator<Map.Entry<String, JsonNode>> fields = ratesNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String currencyCode = entry.getKey();
                BigDecimal rate = entry.getValue().decimalValue();

                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setCurrencyCode(currencyCode);
                exchangeRate.setRate(rate);
                exchangeRate.setCreatedAt(LocalDateTime.now());

                repository.save(exchangeRate);
            }
        } catch (Exception e) {
            log.error("Error fetching exchange rates", e);
        }
    }
}
