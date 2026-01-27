package com.gabriel.cryptodashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.cryptodashboard.entidades.Transaction;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, Long>{
	List<Transaction> findByWalletId(Long id);

}
