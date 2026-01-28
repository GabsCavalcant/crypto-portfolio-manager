package com.gabriel.cryptodashboard.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.gabriel.cryptodashboard.dto.TransactionDto;
import com.gabriel.cryptodashboard.entidades.Asset;
import com.gabriel.cryptodashboard.entidades.Transaction;
import com.gabriel.cryptodashboard.entidades.Wallet;
import com.gabriel.cryptodashboard.repository.AssetRepository;
import com.gabriel.cryptodashboard.repository.TransactionRepository;
import com.gabriel.cryptodashboard.repository.WalletRepository;

@Service
public class TransactionService {
	
	private final TransactionRepository transactionRepository;
	
	private final WalletRepository walletRepository;
	
	private final AssetRepository assetRepository;

	public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository,
			AssetRepository assetRepository) {
		super();
		this.transactionRepository = transactionRepository;
		this.walletRepository = walletRepository;
		this.assetRepository = assetRepository;
	}
	
	public Transaction create(TransactionDto dto) {
		Wallet wallet = walletRepository.findById(dto.walletId()).orElseThrow(() -> new RuntimeException(
				"Carteira não encontrada ID: " + dto.walletId()));
		Asset asset = assetRepository.findBySymbol(dto.assetSymbol());
		if (asset == null) {
            throw new RuntimeException("Ativo não encontrado: " + dto.assetSymbol());
        }

        // 3. Converter DTO para Entidade
        Transaction transaction = new Transaction();
        transaction.setTimestamp(LocalDateTime.now()); // Data de agora
        transaction.setWallet(wallet);
        transaction.setAsset(asset);
        transaction.setQuantity(dto.quantity());
        transaction.setPricePerUnit(dto.price());
        transaction.setType(dto.type());
        
        // Calcular o valor total automaticamente
        transaction.setTotalValue(dto.quantity().multiply(dto.price()));

        // 4. Salvar
        return transactionRepository.save(transaction);
	}
	
	

}
