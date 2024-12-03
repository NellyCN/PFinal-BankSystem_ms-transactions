package com.xyzbank.mstransactions.mstransactions.service.impl;

import com.xyzbank.mstransactions.mstransactions.dto.TransactionDTO;
import com.xyzbank.mstransactions.mstransactions.mapper.TransactionMapper;
import com.xyzbank.mstransactions.mstransactions.model.Transaction;
import com.xyzbank.mstransactions.mstransactions.repository.TransactionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import static org.mockito.Mockito.*;


class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;    // Mock del Repositorio

    @Mock
    private WebClient webClient;

    @InjectMocks
    private TransactionServiceImpl transactionService;      // Servicio bajo prueba

    @Mock
    private TransactionMapper transactionMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    //TEST HISTORIAL DE TRANSACCIONES
    @Test
    void getAllTransactions_Success() {
        // Datos simulados para el repositorio y el mapper
        Transaction transaction1 = new Transaction("1", "Deposit", 100.0, LocalDateTime.now(), "Completed", null, "12345600");
        Transaction transaction2 = new Transaction("2", "Withdrawal", 50.0, LocalDateTime.now(), "Completed", "12345600", null);
        TransactionDTO transactionDTO1 = new TransactionDTO("1", "Deposit", 100.0, LocalDateTime.now(), "Completed", null, "12345600");
        TransactionDTO transactionDTO2 = new TransactionDTO("2", "Withdrawal", 50.0, LocalDateTime.now(), "Completed", "12345600", null);

        // Mockeo del repositorio para devolver las transacciones
        when(transactionRepository.findAll()).thenReturn(Flux.just(transaction1, transaction2));
        // Mockeo del mapper para convertir las entidades a DTOs
        when(transactionMapper.toDTO(transaction1)).thenReturn(transactionDTO1);
        when(transactionMapper.toDTO(transaction2)).thenReturn(transactionDTO2);

        // Ejecución del método a probar
        Flux<TransactionDTO> result = transactionService.getAllTransactions();

        // Verificación del resultado utilizando StepVerifier
        StepVerifier.create(result)
                .expectNext(transactionDTO1)
                .expectNext(transactionDTO2)
                .verifyComplete();

        // Verificación de interacciones con mocks
        verify(transactionRepository, times(1)).findAll();
        verify(transactionMapper, times(1)).toDTO(transaction1);
        verify(transactionMapper, times(1)).toDTO(transaction2);
    }
}