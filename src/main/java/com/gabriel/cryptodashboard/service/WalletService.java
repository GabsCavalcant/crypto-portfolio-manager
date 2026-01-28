package com.gabriel.cryptodashboard.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gabriel.cryptodashboard.dto.PortfolioItemDto;
import com.gabriel.cryptodashboard.entidades.Transaction;
import com.gabriel.cryptodashboard.entidades.enums.TransactionType;
import com.gabriel.cryptodashboard.repository.TransactionRepository;

@Service
public class WalletService {

	private final TransactionRepository transactionRepository;

	public WalletService(TransactionRepository transactionRepository) {

		this.transactionRepository = transactionRepository;
	}

	// Listar Todas as transacoes da carteira.
	public List<PortfolioItemDto> getPortfolio(Long id) {
		List<Transaction> transactions = transactionRepository.findByWalletId(id);

		// Utilização do Map para Unica chave
		Map<String, List<Transaction>> transactionsByAsset = transactions.stream()
				.collect(Collectors.groupingBy(t -> t.getAsset().getSymbol()));

		List<PortfolioItemDto> portfolio = new ArrayList<>();
		// Para cada ativo, calcular o saldo
		for (var entry : transactionsByAsset.entrySet()) {
			String symbol = entry.getKey();
			List<Transaction> transacoesDoAtivo = entry.getValue();

			BigDecimal quantidadeTotal = BigDecimal.ZERO;
			BigDecimal valorInvestido = BigDecimal.ZERO;
			String nomeAtivo = "";

			for (Transaction t : transacoesDoAtivo) {
				nomeAtivo = t.getAsset().getName();

				// Lógica simples: COMPRA soma, VENDA subtrai
				if (t.getType() == TransactionType.BUY) {
					quantidadeTotal = quantidadeTotal.add(t.getQuantity());
					valorInvestido = valorInvestido.add(t.getTotalValue());
				} else if (t.getType() == TransactionType.SELL) {
					quantidadeTotal = quantidadeTotal.subtract(t.getQuantity());

					// Na venda, a lógica do valor investido é mais complexa,
					// mas por enquanto vou apenas subtrair proporcionalmente ou deixar simples
					valorInvestido = valorInvestido.subtract(t.getTotalValue());
				}

			}

			if (quantidadeTotal.compareTo(BigDecimal.ZERO) > 0) {
				portfolio.add(new PortfolioItemDto(symbol, nomeAtivo, quantidadeTotal, valorInvestido));
			}

			

		}
		return portfolio;
	}

}
