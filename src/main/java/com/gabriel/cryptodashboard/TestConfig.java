package com.gabriel.cryptodashboard;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gabriel.cryptodashboard.entidades.Asset;
import com.gabriel.cryptodashboard.entidades.Transaction;
import com.gabriel.cryptodashboard.entidades.Wallet;
import com.gabriel.cryptodashboard.entidades.enums.AssetType;
import com.gabriel.cryptodashboard.entidades.enums.TransactionType;
import com.gabriel.cryptodashboard.repository.AssetRepository;
import com.gabriel.cryptodashboard.repository.TransactionRepository;
import com.gabriel.cryptodashboard.repository.WalletRepository;

@Configuration
public class TestConfig {

	@Bean
	public CommandLineRunner run(AssetRepository assetRepo, WalletRepository walletRepo,
			TransactionRepository transRepo) {
		return args -> {
			
			if (walletRepo.count() > 0) {
                System.out.println("---------------------------------");
                System.out.println("BANCO JÁ POPULADO. PULANDO CARGA INICIAL.");
                System.out.println("---------------------------------");
                return; 
            }
			// 1. Criar Carteira
			Wallet w1 = new Wallet();
			w1.setName("Minha Trezor");
			walletRepo.save(w1);

			// 2. Criar Ativos
			Asset btc = new Asset();
			btc.setSymbol("BTC");
			btc.setName("Bitcoin");
			btc.setType(AssetType.CRYPTO); // Assumindo que você criou esse Enum

			Asset brl = new Asset();
			brl.setSymbol("BRL");
			brl.setName("Real Brasileiro");
			brl.setType(AssetType.FIAT);

			assetRepo.saveAll(Arrays.asList(btc, brl));

			// 3. Criar uma Transação (Compra de BTC)
			Transaction t1 = new Transaction();
			t1.setTimestamp(LocalDateTime.now());
			t1.setWallet(w1);
			t1.setAsset(btc);
			t1.setType(TransactionType.BUY); // Assumindo seu Enum
			t1.setQuantity(new BigDecimal("0.005")); // 0.005 BTC
			t1.setPricePerUnit(new BigDecimal("350000.00")); // Pagou 350k no un
			t1.setTotalValue(new BigDecimal("1750.00")); // Total R$ 1750

			transRepo.save(t1);

			System.out.println("---------------------------------");
			System.out.println("BANCO DE DADOS INICIADO COM SUCESSO!");
			System.out.println("Carteira criada: " + w1.getId());
			System.out.println("Transação registrada: " + t1.getTotalValue());
			System.out.println("---------------------------------");
		};
	}
}