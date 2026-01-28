package com.gabriel.cryptodashboard.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.cryptodashboard.dto.TransactionDto;
import com.gabriel.cryptodashboard.entidades.Transaction;
import com.gabriel.cryptodashboard.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction criarTransacao(@RequestBody TransactionDto dto) {
        return transactionService.create(dto);
    }
}