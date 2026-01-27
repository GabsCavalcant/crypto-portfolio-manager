package com.gabriel.cryptodashboard.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gabriel.cryptodashboard.entidades.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

	@Entity
	@Table(name = "tb_transactions")
	public class Transaction {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private LocalDateTime timestamp;

	    // Relacionamento: Muitas transações para Uma carteira
	    @ManyToOne
	    @JoinColumn(name = "wallet_id", nullable = false)
	    private Wallet wallet;

	    // Relacionamento: Muitas transações para Um ativo
	    @ManyToOne
	    @JoinColumn(name = "asset_id", nullable = false)
	    private Asset asset;

	    // Tipo da transação: COMPRA, VENDA, ETC.
	    @Enumerated(EnumType.STRING)
	    private TransactionType type; 

	   
	    // Precision 19, Scale 8 garante até 8 casas decimais
	    @Column(nullable = false, precision = 19, scale = 8)
	    private BigDecimal quantity;

	    // Preço pago por unidade no momento (Ex: R$ 350.000,00)
	    @Column(nullable = false, precision = 19, scale = 2)
	    private BigDecimal pricePerUnit;

	    // Valor total da operação (quantity * pricePerUnit)
	    @Column(nullable = false, precision = 19, scale = 2)
	    private BigDecimal totalValue;

	    public Transaction() {
	    	
	    }
	    
	    
		public Transaction(Long id, LocalDateTime timestamp, Wallet wallet, Asset asset, TransactionType type,
				BigDecimal quantity, BigDecimal pricePerUnit, BigDecimal totalValue) {
			super();
			this.id = id;
			this.timestamp = timestamp;
			this.wallet = wallet;
			this.asset = asset;
			this.type = type;
			this.quantity = quantity;
			this.pricePerUnit = pricePerUnit;
			this.totalValue = totalValue;
		}


		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

		public Wallet getWallet() {
			return wallet;
		}

		public void setWallet(Wallet wallet) {
			this.wallet = wallet;
		}

		public Asset getAsset() {
			return asset;
		}

		public void setAsset(Asset asset) {
			this.asset = asset;
		}

		public TransactionType getType() {
			return type;
		}

		public void setType(TransactionType type) {
			this.type = type;
		}

		public BigDecimal getQuantity() {
			return quantity;
		}

		public void setQuantity(BigDecimal quantity) {
			this.quantity = quantity;
		}

		public BigDecimal getPricePerUnit() {
			return pricePerUnit;
		}

		public void setPricePerUnit(BigDecimal pricePerUnit) {
			this.pricePerUnit = pricePerUnit;
		}

		public BigDecimal getTotalValue() {
			return totalValue;
		}

		public void setTotalValue(BigDecimal totalValue) {
			this.totalValue = totalValue;
		}
	    
	    
	}
	
	
