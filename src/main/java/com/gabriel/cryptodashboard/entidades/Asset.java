package com.gabriel.cryptodashboard.entidades;

import com.gabriel.cryptodashboard.entidades.enums.AssetType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "tb_assets")
public class Asset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, unique = true)
	private String symbol;
	
	@Column(nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	private AssetType type;
	
	public Asset() {
		
	}

	public Asset(Integer id, String symbol, String name, AssetType type) {
		super();
		this.id = id;
		this.symbol = symbol;
		this.name = name;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AssetType getType() {
		return type;
	}

	public void setType(AssetType type) {
		this.type = type;
	}
	

}
