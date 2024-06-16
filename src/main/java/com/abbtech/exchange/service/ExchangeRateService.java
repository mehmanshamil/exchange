package com.abbtech.exchange.service;

import com.abbtech.exchange.model.ExchangeRate;

import java.util.Optional;

public interface ExchangeRateService {
    Optional<ExchangeRate> getLatestRate(String currencyCode);
}

