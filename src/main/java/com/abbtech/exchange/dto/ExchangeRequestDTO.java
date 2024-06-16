package com.abbtech.exchange.dto;


import com.abbtech.exchange.type.CurrencyType;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRequestDTO {
    private Double amount;
    private CurrencyType currencyType;
}
