package com.xyzbank.mstransactions.mstransactions.service.impl;

import com.xyzbank.mstransactions.mstransactions.model.Transaction;
import com.xyzbank.mstransactions.mstransactions.repository.TransactionRepository;
import com.xyzbank.mstransactions.mstransactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    // Implementación de métodos
    private final TransactionRepository transactionRepository;

    @Override
    public Mono<Transaction> deposit(Transaction transaction) {
        // Validar que el monto sea positivo.
        if (transaction.getAmount() <= 0) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto debe ser positivo"));
        }

        // Crear la transacción de depósito.
        transaction.setType("Deposit");
        transaction.setDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    @Override
    public Mono<Transaction> withdrawal(Transaction transaction) {
        // Validar que el monto sea positivo.
        if (transaction.getAmount() <= 0) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto debe ser positivo"));
        }

        // Simulo validación de saldo
        Double currentBalance = 1000.0; // Saldo fijo
        if (currentBalance < transaction.getAmount()) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente"));
        }

// Crear la transacción de retiro.
        transaction.setType("Withdrawal");
        transaction.setDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    @Override
    public Mono<Transaction> transfer(Transaction transaction) {
        // Valido datos requeridos.
        if (transaction.getAmount() <= 0 || transaction.getDestinationAccount() == null) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos inválidos para la transferencia"));
        }

        // Simulo validación de saldo.
        Double currentBalance = 1000.0; // Saldo Fijo
        if (currentBalance < transaction.getAmount()) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente"));
        }

        // Crear la transacción de transferencia.
        transaction.setType("Transfer");
        transaction.setDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    @Override
    public Flux<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

}
