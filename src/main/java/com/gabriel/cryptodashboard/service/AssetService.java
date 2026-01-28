package com.gabriel.cryptodashboard.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gabriel.cryptodashboard.entidades.Asset;
import com.gabriel.cryptodashboard.repository.AssetRepository;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

    // --- A MUDANÇA É AQUI ---
    public Asset save(Asset asset) {
        // 1. Antes de salvar, busca se já existe pelo Símbolo (Ex: BTC)
        Asset existing = assetRepository.findBySymbol(asset.getSymbol());
        
        // 2. Se existir, retorna o que achou e NÃO salva de novo
        if (existing != null) {
            return existing;
        }
        
        // 3. Se não existir, aí sim salva
        return assetRepository.save(asset);
    }
}