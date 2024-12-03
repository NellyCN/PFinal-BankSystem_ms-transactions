package com.xyzbank.mstransactions.mstransactions.service;

import com.xyzbank.mstransactions.mstransactions.dto.TransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<TransactionDTO> deposit(TransactionDTO transactionDTO); // Realizar depósito.
    Mono<TransactionDTO> withdrawal(TransactionDTO transactionDTO); // Realizar retiro.
    Mono<TransactionDTO> transfer(TransactionDTO transactionDTO); // Realizar transferencia.
    Flux<TransactionDTO> getAllTransactions();
//    Flux<TransactionDTO> getAllTransactionsAccount(String accountId);  // Consultar historial de transacciones.

}
