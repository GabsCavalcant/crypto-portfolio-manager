package com.gabriel.cryptodashboard.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.gabriel.cryptodashboard.dto.PortfolioItemDto;
import com.gabriel.cryptodashboard.entidades.Transaction;
import com.gabriel.cryptodashboard.entidades.enums.TransactionType;
import com.gabriel.cryptodashboard.repository.TransactionRepository;

@Service
public class WalletService {

	private final TransactionRepository transactionRepository;
	
	private final CryptoPriceService cryptoPriceService;

	public WalletService(TransactionRepository transactionRepository, CryptoPriceService cryptoPriceService) {

		this.transactionRepository = transactionRepository;
		this.cryptoPriceService = cryptoPriceService;
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
				
                // 1. Busca o preço atual na internet (R$)
                Double precoAtualDouble = cryptoPriceService.consultarPreco(symbol);
                BigDecimal precoAtual = BigDecimal.valueOf(precoAtualDouble);

                // 2. Calcula quanto vale hoje (Qtd * Preço Atual)
                BigDecimal valorAtualTotal = quantidadeTotal.multiply(precoAtual);

                // 3. Calcula Lucro/Prejuízo %
                // Fórmula: ((ValorAtual - Investido) / Investido) * 100
                BigDecimal lucroPorcentagem = BigDecimal.ZERO;
                if (valorInvestido.compareTo(BigDecimal.ZERO) > 0) {
                     lucroPorcentagem = valorAtualTotal.subtract(valorInvestido)
                            .divide(valorInvestido, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                }

                BigDecimal lucroValor = valorAtualTotal.subtract(valorInvestido);

             // 4. Adicionar na lista com a ordem correta )
             portfolio.add(new PortfolioItemDto(
                     symbol, 
                     nomeAtivo, 
                     quantidadeTotal, 
                     valorAtualTotal,   // Valor Bruto Atual
                     lucroPorcentagem,  // % de Lucro
                     valorInvestido,    // Investido
                     lucroValor         // do Lucro
             ));

			

		}
		
	}
		return portfolio;
	
	}}
