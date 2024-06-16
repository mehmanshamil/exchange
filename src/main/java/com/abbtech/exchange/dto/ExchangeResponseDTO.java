package com.abbtech.exchange.dto;

import com.abbtech.exchange.type.CurrencyType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeResponseDTO{
    private Double amount;
    private CurrencyType fromCurrency;
    private CurrencyType toCurrency;
    private Double convertedAmount;
}
