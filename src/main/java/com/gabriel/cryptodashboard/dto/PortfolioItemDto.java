package com.gabriel.cryptodashboard.dto;

import java.math.BigDecimal;

public record PortfolioItemDto(String symbol, String name, BigDecimal quantity, 
		BigDecimal currentTotalValue, 
	    BigDecimal profitPercentage,
	    BigDecimal totalInvested) {}
