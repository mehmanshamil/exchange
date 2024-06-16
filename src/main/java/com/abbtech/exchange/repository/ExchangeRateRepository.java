package com.abbtech.exchange.repository;


import com.abbtech.exchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findTopByCurrencyCodeOrderByCreatedAtDesc(String currencyCode);
}
