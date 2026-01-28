package com.gabriel.cryptodashboard.dto;

import java.math.BigDecimal;

public record MarketTickerDto(String symbol, String name, BigDecimal currentPrice) {}