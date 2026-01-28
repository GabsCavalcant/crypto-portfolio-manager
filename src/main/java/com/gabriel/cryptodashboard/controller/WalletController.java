package com.gabriel.cryptodashboard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.cryptodashboard.dto.PortfolioItemDto;
import com.gabriel.cryptodashboard.entidades.Wallet;
import com.gabriel.cryptodashboard.repository.WalletRepository;
import com.gabriel.cryptodashboard.service.WalletService;

@RestController
@RequestMapping("/wallets")
public class WalletController {
	
	private WalletService walletService;
	private final WalletRepository walletRepository;
	
	public WalletController(WalletService walletService,WalletRepository walletRepository) {
        this.walletService = walletService;
        this.walletRepository = walletRepository;
	}
        @GetMapping
        public List<Wallet> listarTodas() {
            return walletRepository.findAll();
        }

        
        @PostMapping
        public Wallet criar(@RequestBody Wallet wallet) {
            return walletRepository.save(wallet);
        
    }
	@GetMapping("/{id}/portfolio")
	public List<PortfolioItemDto> getPortfolio(@PathVariable Long id) {
        return walletService.getPortfolio(id);
    }

}
