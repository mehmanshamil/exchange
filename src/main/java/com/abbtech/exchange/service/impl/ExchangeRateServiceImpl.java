package com.abbtech.exchange.service.impl;

import com.abbtech.exchange.model.ExchangeRate;
import com.abbtech.exchange.repository.ExchangeRateRepository;
import com.abbtech.exchange.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository repository;

    @Override
    public Optional<ExchangeRate> getLatestRate(String currencyCode) {
        return repository.findTopByCurrencyCodeOrderByCreatedAtDesc(currencyCode);
    }
}
