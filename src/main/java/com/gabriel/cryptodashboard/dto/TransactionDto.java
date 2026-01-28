package com.gabriel.cryptodashboard.dto;

import java.math.BigDecimal;

import com.gabriel.cryptodashboard.entidades.enums.TransactionType;

public record TransactionDto(
	    Long walletId,       
	    String assetSymbol,  
	    BigDecimal quantity, 
	    BigDecimal price,    
	    TransactionType type 
	) {}
