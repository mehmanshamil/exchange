package com.abbtech.exchange.controller;

import com.abbtech.exchange.dto.ExchangeRequestDTO;
import com.abbtech.exchange.dto.ExchangeResponseDTO;
import com.abbtech.exchange.service.ExchangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping()
    public String foo() {
        return "called api";
    }

    @PostMapping("/from-AZN")
    @ResponseStatus(HttpStatus.OK)
    public ExchangeResponseDTO fromAZN(@Valid @RequestBody ExchangeRequestDTO exchangeResponseDto) {
        return exchangeService.fromAZN(exchangeResponseDto);
    }

    @PostMapping("/to-AZN")
    @ResponseStatus(HttpStatus.OK)
    public ExchangeResponseDTO toAZN(@Valid @RequestBody ExchangeRequestDTO exchangeResponseDto) {
        return exchangeService.toAZN(exchangeResponseDto);
    }

}
