package com.gabriel.cryptodashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.cryptodashboard.entidades.Wallet;
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long>{
	
	

}
