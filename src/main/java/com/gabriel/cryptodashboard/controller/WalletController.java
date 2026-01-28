package com.gabriel.cryptodashboard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.cryptodashboard.dto.PortfolioItemDto;
import com.gabriel.cryptodashboard.service.WalletService;

@RestController
@RequestMapping("/wallets")
public class WalletController {
	
	private WalletService walletService;
	
	public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
	@GetMapping("/{id}/portfolio")
	public List<PortfolioItemDto> getPortfolio(@PathVariable Long id) {
        return walletService.getPortfolio(id);
    }

}
