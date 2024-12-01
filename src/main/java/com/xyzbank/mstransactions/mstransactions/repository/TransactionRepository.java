package com.xyzbank.mstransactions.mstransactions.repository;

import com.xyzbank.mstransactions.mstransactions.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    // Metodo para obtener transacciones por cuenta origen
    Flux<Transaction> findBySourceAccount(String sourceAccount);
}
