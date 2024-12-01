package com.xyzbank.mstransactions.mstransactions.service.impl;

import com.xyzbank.mstransactions.mstransactions.exception.CustomException;
import com.xyzbank.mstransactions.mstransactions.model.Transaction;
import com.xyzbank.mstransactions.mstransactions.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;    // Mock del Repositorio

    @Mock
    private WebClient webClient;

    @InjectMocks
    private TransactionServiceImpl transactionService;      // Servicio bajo prueba

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeposit() {
        // Mock de transacción
        Transaction transaction = new Transaction(null, "Deposit", 100.0, LocalDateTime.now(), null, "123", null);

        // Mock del repositorio
        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));

        // Verificación del metodo deposit, probamos el mock con StepVerifier
        StepVerifier.create(transactionService.deposit(transaction))
                // Se espera los valores en t
                .expectNext(transaction)
                .verifyComplete();  // Se completa la verificación

        verify(transactionRepository, times(1)).save(transaction);
    }

//    @Test
//    void testWithdraw_InsufficientBalance() {
//        // Mock de transacción
//        Transaction transaction = new Transaction(null, "Withdrawal", 200.0, LocalDateTime.now(), null, "123", null);
//
//        // Mock de WebClient para saldo insuficiente
//        when(webClient.get().uri("/accounts/{id}/balance", "123").retrieve().bodyToMono(Double.class))
//                .thenReturn(Mono.just(100.0));
//
//        // Verificación del metodo withdraw con saldo insuficiente
//        StepVerifier.create(transactionService.withdrawal(transaction))
//                .expectError(CustomException.class)
//                .verify();
//
//        verify(transactionRepository, never()).save(any());
//    }
//
//    @Test
//    void testTransfer_SufficientBalance() {
//        // Mock de transacción
//        Transaction transaction = new Transaction(null, "Transfer", 50.0, LocalDateTime.now(), null, "123", "456");
//
//        // Mock de WebClient para saldo suficiente
//        when(webClient.get().uri("/accounts/{id}/balance", "123").retrieve().bodyToMono(Double.class))
//                .thenReturn(Mono.just(100.0));
//
//        // Mock del repositorio
//        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));
//
//        // Verificación del metodo transfer
//        StepVerifier.create(transactionService.transfer(transaction))
//                .expectNext(transaction)
//                .verifyComplete();
//
//        verify(transactionRepository, times(1)).save(transaction);
//    }

    //TEST HISTORIAL DE TRANSACCIONES
    @Test
    void testGetAllTransactions() {
        // Mock de transacciones
        when(transactionRepository.findAll()).thenReturn(Flux.just(
                new Transaction("1", "DEPOSIT", 100.00, LocalDateTime.now(), "COMPLETED", null, "12345678"),
                new Transaction("2", "WITHDRAWAL", 50.00, LocalDateTime.now(), "COMPLETED", "12345600", null),
                new Transaction("3", "TRANSFER", 150.00, LocalDateTime.now(), "COMPLETED", "12345600", "12345678")
        ));

        // Verificación con StepVerifier
        StepVerifier.create(transactionService.getAllTransactions())
                .expectNextMatches(transaction -> transaction.getId().equals("1"))
                .expectNextMatches(transaction -> transaction.getId().equals("2"))
                .expectNextMatches(transaction -> transaction.getId().equals("3"))
                .verifyComplete();
    }

    @Test
    void testGetTransactionHistory() {
        // Mock de transacciones
        Transaction transaction1 = new Transaction("1", "DEPOSIT", 100.0, LocalDateTime.now(), "Completado", "123", null);
        Transaction transaction2 = new Transaction("2", "WITHDRAWAL", 50.0, LocalDateTime.now(), "Completado", "123", null);

        // Mock del repositorio
        when(transactionRepository.findBySourceAccount("123")).thenReturn(Flux.fromIterable(Arrays.asList(transaction1, transaction2)));

        // Verificación del historial de transacciones
        StepVerifier.create(transactionService.getAllTransactionsAccount("123"))
                // Se espera los valores en t1 y t2
                .expectNext(transaction1)
                .expectNext(transaction2)
                .verifyComplete();      // Se inicia la verificación

        verify(transactionRepository, times(1)).findBySourceAccount("123");     // Se verifica la ubicación de la cuenta de Origen.
    }
}
