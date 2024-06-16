package com.abbtech.exchange.service.impl;

import com.abbtech.exchange.dto.ExchangeRequestDTO;
import com.abbtech.exchange.dto.ExchangeResponseDTO;
import com.abbtech.exchange.exception.UnsupportedCurrencyException;
import com.abbtech.exchange.model.ExchangeRate;
import com.abbtech.exchange.service.ExchangeRateService;
import com.abbtech.exchange.service.ExchangeService;
import com.abbtech.exchange.type.CurrencyType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeRateService exchangeRateService;
    private static final Map<CurrencyType, BigDecimal> defaultRates = new EnumMap<>(CurrencyType.class);

    @Override
    public ExchangeResponseDTO fromAZN(ExchangeRequestDTO exchange) {
        return performExchange(exchange, true);
    }

    @Override
    public ExchangeResponseDTO toAZN(ExchangeRequestDTO exchange) {
        return performExchange(exchange, false);
    }

    private ExchangeResponseDTO performExchange(ExchangeRequestDTO exchange, boolean isFromAZN) {
        log.info("Performing exchange {} {} with amount {}", isFromAZN ? "from AZN to" : "to AZN from", exchange.getCurrencyType(),
                exchange.getAmount());

        BigDecimal rate = fetchRate(exchange.getCurrencyType());
        if (rate == null) {
            throw new UnsupportedCurrencyException("Unsupported currency: " + exchange.getCurrencyType());
        }

        BigDecimal originalAmount = BigDecimal.valueOf(exchange.getAmount());
        BigDecimal convertedAmount = isFromAZN ? originalAmount.divide(rate, 2, RoundingMode.HALF_EVEN) :
                originalAmount.multiply(rate).setScale(2, RoundingMode.HALF_EVEN);

        log.info("Successfully performing exchange {} {} with amount {}", isFromAZN ? "from AZN to" : "to AZN from",
                exchange.getCurrencyType(), exchange.getAmount());

        return ExchangeResponseDTO.builder()
                .amount(exchange.getAmount())
                .fromCurrency(isFromAZN ? CurrencyType.AZN : exchange.getCurrencyType())
                .toCurrency(isFromAZN ? exchange.getCurrencyType() : CurrencyType.AZN)
                .convertedAmount(convertedAmount.doubleValue())
                .build();
    }

    private BigDecimal fetchRate(CurrencyType currencyType) {
        return exchangeRateService.getLatestRate(currencyType.name())
                .map(ExchangeRate::getRate)
                .orElse(defaultRates.get(currencyType));
    }
}
