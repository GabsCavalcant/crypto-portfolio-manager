package com.gabriel.cryptodashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.cryptodashboard.entidades.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
	//Achar Pelo simbolo
	Asset findBySymbol(String symbol);

}
