package com.xyzbank.mstransactions.mstransactions.service.impl;

import com.xyzbank.mstransactions.mstransactions.dto.TransactionDTO;
import com.xyzbank.mstransactions.mstransactions.mapper.TransactionMapper;
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
    private final TransactionMapper transactionMapper;
    private final WebClient webClient;

    // Constructor con parámetros requeridos
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, WebClient webClient) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.webClient = webClient;
    }

//    Obtener Saldo de una Cuenta
    private Mono<Double> getAccountBalance(String accountId) {
        return webClient.get()
                .uri("/accounts/{id}/balance", accountId) // Endpoint para consultar saldo
                .retrieve()
                .bodyToMono(Double.class) // Convierte la respuesta en un valor Double
                .onErrorResume(e -> Mono.error(new CustomException("Error al obtener el saldo de la cuenta: " + e.getMessage())));
    }

    // DEPOSITO

    @Override
    public Mono<TransactionDTO> deposit(TransactionDTO transactionDTO) {
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction.setDate(LocalDateTime.now());
        transaction.setType("DEPOSIT");
        transaction.setStatus("COMPLETED");
        return transactionRepository.save(transaction).map(transactionMapper::toDTO);
    }

    // RETIRO
    @Override
    public Mono<TransactionDTO> withdrawal(TransactionDTO transactionDTO) {
        return getAccountBalance(transactionDTO.getId()) // Llama al método para obtener el saldo
                .flatMap(balance -> {
                    if (balance >= transactionDTO.getAmount()) { // Valida saldo suficiente
                        Transaction transaction = transactionMapper.toEntity(transactionDTO);
                        transaction.setDate(LocalDateTime.now());
                        transaction.setType("WITHDRAWAL");
                        transaction.setStatus("COMPLETED");
                        return transactionRepository.save(transaction).map(transactionMapper::toDTO);
                    } else {
                        // Retorna un error si no hay saldo suficiente
                        return Mono.error(new CustomException("Saldo insuficiente para realizar el retiro."));
                    }
                });
    }

    // TRANSFERENCIA
    @Override
    public Mono<TransactionDTO> transfer(TransactionDTO transactionDTO) {
        return getAccountBalance(transactionDTO.getId()) // Llama al método para obtener el saldo
                .flatMap(balance -> {
                    if (balance >= transactionDTO.getAmount()) { // Valida saldo suficiente
                        Transaction transaction = transactionMapper.toEntity(transactionDTO);
                        transaction.setDate(LocalDateTime.now());
                        transaction.setType("TRANSFER");
                        transaction.setStatus("COMPLETED");
                        return transactionRepository.save(transaction).map(transactionMapper::toDTO);
                    } else {
                        // Retorna un error si no hay saldo suficiente
                        return Mono.error(new CustomException("Saldo insuficiente para realizar la transferencia."));
                    }
                });
    }

    // HISTORIAL DE TRANSACCIONES
    @Override
    public Flux<TransactionDTO> getAllTransactions() {
        // Asegura que el repositorio nunca devuelva null.
        return transactionRepository.findAll().map(transactionMapper::toDTO);
    }

    @Override
    public Flux<TransactionDTO> getAllTransactionsAccount(String accountId) {
        return transactionRepository.findBySourceAccount(accountId).map(transactionMapper::toDTO);
    }

}