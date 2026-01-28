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

    public Asset save(Asset asset) {
        // Exemplo de regra de negócio futura:
        // if (assetRepository.findBySymbol(asset.getSymbol()) != null) { ... lança erro ... }
        return assetRepository.save(asset);
    }
}