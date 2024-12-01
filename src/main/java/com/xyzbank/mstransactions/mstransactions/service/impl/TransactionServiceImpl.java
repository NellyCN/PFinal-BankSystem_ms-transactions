package com.xyzbank.mstransactions.mstransactions.service.impl;

import com.xyzbank.mstransactions.mstransactions.model.Transaction;
import com.xyzbank.mstransactions.mstransactions.repository.TransactionRepository;
import com.xyzbank.mstransactions.mstransactions.service.TransactionService;
import com.xyzbank.mstransactions.mstransactions.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WebClient webClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository, WebClient webClient) {
        this.transactionRepository = transactionRepository;
        this.webClient = webClient;
    }


    private Mono<Double> getAccountBalance(String accountId) {
        return webClient.get()
                .uri("/accounts/{id}/balance", accountId)
                .retrieve()
                .bodyToMono(Double.class)
                .onErrorResume(e -> Mono.error(new CustomException("Error al obtener el saldo de la cuenta")));
    }

    // DEPOSITO

    @Override
    public Mono<Transaction> deposit(Transaction transaction) {
        transaction.setDate(LocalDateTime.now());
        transaction.setType("DEPOSIT");
        transaction.setStatus("COMPLETED");
        return transactionRepository.save(transaction);
    }

    // RETIRO
    @Override
    public Mono<Transaction> withdrawal(Transaction transaction) {
        return getAccountBalance(transaction.getSourceAccount())
                .flatMap(balance -> {
                    if (balance >= transaction.getAmount()) {
                        transaction.setDate(LocalDateTime.now());
                        transaction.setType("WITHDRAWAL");
                        transaction.setStatus("COMPLETED");
                        return transactionRepository.save(transaction);
                    } else {
                        return Mono.error(new CustomException("Saldo insuficiente para el retiro"));
                    }
                });
    }

    // TRANSFERENCIA
    @Override
    public Mono<Transaction> transfer(Transaction transaction) {
        return getAccountBalance(transaction.getSourceAccount())
                .flatMap(balance -> {
                    if (balance >= transaction.getAmount()) {
                        transaction.setDate(LocalDateTime.now());
                        transaction.setType("TRANSFER");
                        transaction.setStatus("COMPLETED");
                        return transactionRepository.save(transaction);
                    } else {
                        return Mono.error(new CustomException("Saldo insuficiente para la transferencia"));
                    }
                });
    }

    // HISTORIAL DE TRANSACCIONES
    @Override
    public Flux<Transaction> getAllTransactions() {
        // Asegura que el repositorio nunca devuelva null.
        return transactionRepository.findAll() != null ? transactionRepository.findAll() : Flux.empty();
    }

    @Override
    public Flux<Transaction> getAllTransactionsAccount(String accountId) {
        return transactionRepository.findBySourceAccount(accountId);
    }

}