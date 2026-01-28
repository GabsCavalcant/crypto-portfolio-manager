package com.gabriel.cryptodashboard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CryptoPriceService {

    // 1. O Cache: Guarda o preço e a hora da última consulta
    private final Map<String, Double> cachePrecos = new HashMap<>();
    private final Map<String, Long> cacheTempo = new HashMap<>();
    
    // Tempo de vida do cache (em milissegundos)
    // 60.000 ms = 1 minuto. O sistema só vai na internet se o preço for mais velho que isso.
    private static final long TEMPO_EXPIRACAO = 60000; 

    public Double consultarPreco(String coinId) {
        // Normaliza para minúsculo para evitar chaves duplicadas (Bitcoin vs bitcoin)
        String id = coinId.toLowerCase();

        // 2. Verifica se já temos esse preço salvo e se ele ainda é "fresco"
        if (cachePrecos.containsKey(id) && cacheTempo.containsKey(id)) {
            long ultimaVez = cacheTempo.get(id);
            if (System.currentTimeMillis() - ultimaVez < TEMPO_EXPIRACAO) {
                System.out.println("LOG: Usando preço do CACHE para: " + id);
                return cachePrecos.get(id);
            }
        }

        // 3. Se não tem no cache (ou venceu), vai na API buscar
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=" + id + "&vs_currencies=brl";
        RestTemplate restTemplate = new RestTemplate();

        try {
            System.out.println("LOG: Buscando na API CoinGecko: " + id);
            Map<String, Map<String, Double>> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey(id)) {
                Double preco = response.get(id).get("brl");
                
                // Salva no cache para a próxima vez
                cachePrecos.put(id, preco);
                cacheTempo.put(id, System.currentTimeMillis());
                
                return preco;
            }
        } catch (HttpClientErrorException.TooManyRequests e) {
            System.err.println("ERRO 429: Calma! Muitas requisições. Usando último valor conhecido se existir.");
            // Se formos bloqueados, tentamos devolver o valor antigo se tivermos
            if (cachePrecos.containsKey(id)) return cachePrecos.get(id);
            return 0.0;
        } catch (Exception e) {
            System.err.println("Erro ao buscar preço para " + id + ": " + e.getMessage());
        }

        // Se der tudo errado e não tiver cache, retorna 0
        return cachePrecos.getOrDefault(id, 0.0);
    }
}