package com.xyzbank.mstransactions.mstransactions.service;

import com.xyzbank.mstransactions.mstransactions.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Flux<Transaction> getAllTransactionsAccount(String accountId);  // Consultar historial de transacciones.
    Flux<Transaction> getAllTransactions();
    Mono<Transaction> deposit(Transaction transaction); // Realizar depósito.
    Mono<Transaction> withdrawal(Transaction transaction); // Realizar retiro.
    Mono<Transaction> transfer(Transaction transaction); // Realizar transferencia.

}
