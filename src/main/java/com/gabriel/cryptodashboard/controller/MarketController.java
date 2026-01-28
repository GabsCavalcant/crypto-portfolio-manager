package com.gabriel.cryptodashboard.controller;

import com.gabriel.cryptodashboard.dto.MarketTickerDto;
import com.gabriel.cryptodashboard.service.CryptoPriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final CryptoPriceService cryptoPriceService;

    public MarketController(CryptoPriceService cryptoPriceService) {
        this.cryptoPriceService = cryptoPriceService;
    }

    @GetMapping("/top")
    public List<MarketTickerDto> getTopCryptos() {
        List<MarketTickerDto> market = new ArrayList<>();
        
        // Lista manual das principais (pode adicionar mais se quiser)
        // O primeiro argumento é o ID da API (ex: CoinGecko), o segundo é o Nome para exibir
        market.add(criarTicker("bitcoin", "Bitcoin (BTC)"));
        market.add(criarTicker("ethereum", "Ethereum (ETH)"));
        market.add(criarTicker("solana", "Solana (SOL)"));
        market.add(criarTicker("tether", "Dólar (USDT)")); // Para ver a cotação do dólar cripto

        return market;
    }

    private MarketTickerDto criarTicker(String idApi, String nomeExibicao) {
        try {
            Double preco = cryptoPriceService.consultarPreco(idApi); // Usa seu serviço existente
            return new MarketTickerDto(idApi, nomeExibicao, BigDecimal.valueOf(preco));
        } catch (Exception e) {
            // Se der erro na API, retorna zero para não quebrar a tela
            return new MarketTickerDto(idApi, nomeExibicao, BigDecimal.ZERO);
        }
    }
}