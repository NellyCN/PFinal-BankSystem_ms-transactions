package com.xyzbank.mstransactions.mstransactions.controller;

import com.xyzbank.mstransactions.mstransactions.model.Transaction;
import com.xyzbank.mstransactions.mstransactions.service.TransactionService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController             // Indica al framework que esta clase se usará para recibir peticiones HTTP
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public Flux<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions(); // Llama al servicio para obtener todas las transacciones
    }

}