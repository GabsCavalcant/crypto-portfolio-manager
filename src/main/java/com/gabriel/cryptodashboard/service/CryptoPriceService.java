package com.gabriel.cryptodashboard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@Service
public class CryptoPriceService {

    // 1. Cache para evitar bloquear a API (Guarda por 60s)
    private final Map<String, Double> cachePrecos = new HashMap<>();
    private final Map<String, Long> cacheTempo = new HashMap<>();
    private static final long TEMPO_EXPIRACAO = 60000; // 1 minuto

    public Double consultarPreco(String assetSymbol) {
        
        // --- CORREÇÃO 1: Traduzir Símbolo (BTC) para ID da API (bitcoin) ---
        // Se não tiver nesse mapa, tenta usar o próprio símbolo minúsculo
        String coinId = switch (assetSymbol.toUpperCase()) {
            case "BTC" -> "bitcoin";
            case "ETH" -> "ethereum";
            case "SOL" -> "solana";
            case "USDT" -> "tether";
            case "BNB" -> "binancecoin";
            default -> assetSymbol.toLowerCase(); 
        };

        // 2. Verifica Cache (Se já consultou a menos de 1 min, usa o salvo)
        if (cachePrecos.containsKey(coinId) && cacheTempo.containsKey(coinId)) {
            long ultimaVez = cacheTempo.get(coinId);
            if (System.currentTimeMillis() - ultimaVez < TEMPO_EXPIRACAO) {
                // System.out.println("LOG: Usando CACHE para: " + coinId); // Comentei pra limpar o console
                return cachePrecos.get(coinId);
            }
        }

        // 3. Configura a requisição para fingir ser um navegador (Evita Erro 403)
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=" + coinId + "&vs_currencies=brl";
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();
            // Esse header é OBRIGATÓRIO na CoinGecko hoje em dia
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            // System.out.println("LOG: Buscando na Internet: " + coinId);
            ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            Map response = responseEntity.getBody();

            if (response != null && response.containsKey(coinId)) {
                Map<String, Object> precoMap = (Map<String, Object>) response.get(coinId);
                Object precoObj = precoMap.get("brl");
                
                Double preco = Double.valueOf(precoObj.toString());
                
                // Salva no cache
                cachePrecos.put(coinId, preco);
                cacheTempo.put(coinId, System.currentTimeMillis());
                
                return preco;
            }
        } catch (HttpClientErrorException.TooManyRequests e) {
            System.err.println("ERRO 429: Bloqueio temporário da API. Usando cache antigo se houver.");
            if (cachePrecos.containsKey(coinId)) return cachePrecos.get(coinId);
        } catch (Exception e) {
            System.err.println("Erro ao buscar preço para " + coinId + ": " + e.getMessage());
        }

        // Se falhar tudo, retorna o que tiver no cache ou 0.0
        return cachePrecos.getOrDefault(coinId, 0.0);
    }
}