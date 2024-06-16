package com.abbtech.exchange.service;

import com.abbtech.exchange.dto.ExchangeRequestDTO;
import com.abbtech.exchange.dto.ExchangeResponseDTO;

public interface ExchangeService {
    ExchangeResponseDTO fromAZN(ExchangeRequestDTO exchange);
    ExchangeResponseDTO toAZN(ExchangeRequestDTO exchangeResponseDto);
}