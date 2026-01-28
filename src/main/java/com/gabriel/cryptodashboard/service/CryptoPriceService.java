package com.gabriel.cryptodashboard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient; // Certifique-se de importar este

import java.util.Map;

@Service
public class CryptoPriceService {

    private final RestClient restClient;

    public CryptoPriceService() {
        
        this.restClient = RestClient.create("https://api.coingecko.com/api/v3");
    }

    public Double consultarPreco(String assetSymbol) {
        // Truque simples: CoinGecko usa IDs (bitcoin), nosso banco usa Símbolos (BTC)
        // Em um sistema real, salvaríamos o "slug" no banco, mas aqui vamos improvisar um Map
        String coinId = switch (assetSymbol.toUpperCase()) {
            case "BTC" -> "bitcoin";
            case "ETH" -> "ethereum";
            case "SOL" -> "solana";
            default -> "bitcoin"; // Fallback
        };

        try {
            
            Map response = restClient.get()
                    .uri("/simple/price?ids={id}&vs_currencies=brl", coinId)
                    .retrieve()
                    .body(Map.class);
          
            Map<String, Object> moedaMap = (Map<String, Object>) response.get(coinId);
            Object preco = moedaMap.get("brl");
            
           
            return Double.valueOf(preco.toString());

        } catch (Exception e) {
            System.out.println("Erro ao buscar preço para " + assetSymbol + ": " + e.getMessage());
            return 0.0; 
        }
    }
}