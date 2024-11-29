package com.xyzbank.mstransactions.mstransactions.service;

import com.xyzbank.mstransactions.mstransactions.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Flux<Transaction> getAllTransactions();  // Consultar historial de transacciones.
    Mono<Transaction> deposit(Transaction transaction); // Realizar depósito.
    Mono<Transaction> withdrawal(Transaction transaction); // Realizar retiro.
    Mono<Transaction> transfer(Transaction transaction); // Realizar transferencia.

}
