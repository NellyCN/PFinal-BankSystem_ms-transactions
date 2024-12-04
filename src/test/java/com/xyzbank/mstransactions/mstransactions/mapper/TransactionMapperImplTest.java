package com.xyzbank.mstransactions.mstransactions.mapper;

import com.xyzbank.mstransactions.mstransactions.dto.TransactionDTO;
import com.xyzbank.mstransactions.mstransactions.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class TransactionMapperImplTest {

    private TransactionMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new TransactionMapperImpl();
    }

    @Test
    void testToDTO() {
        // Crear una entidad Transaction
        Transaction transaction = Transaction.builder()
                .id("1")
                .type("DEPOSIT")
                .amount(100.00)
                .date(LocalDateTime.now())
                .status("COMPLETED")
                .sourceAccount("123456")
                .destinationAccount("654321")
                .build();

        // Convertir a DTO
        TransactionDTO transactionDTO = mapper.toDTO(transaction);

        // Verificar resultados
        assertNotNull(transactionDTO);
        assertEquals(transaction.getId(), transactionDTO.getId());
        assertEquals(transaction.getType(), transactionDTO.getType());
        assertEquals(transaction.getAmount(), transactionDTO.getAmount());
        assertEquals(transaction.getDate(), transactionDTO.getDate());
        assertEquals(transaction.getStatus(), transactionDTO.getStatus());
        assertEquals(transaction.getSourceAccount(), transactionDTO.getSourceAccount());
        assertEquals(transaction.getDestinationAccount(), transactionDTO.getDestinationAccount());
    }

    @Test
    void testToEntity() {
        // Crear un DTO
        TransactionDTO transactionDTO = new TransactionDTO(
                "2",
                "WITHDRAWAL",
                50.00,
                LocalDateTime.now(),
                "PENDING",
                "123456",
                "654321"
        );

        // Convertir a entidad
        Transaction transaction = mapper.toEntity(transactionDTO);

        // Verificar resultados
        assertNotNull(transaction);
        assertEquals(transactionDTO.getId(), transaction.getId());
        assertEquals(transactionDTO.getType(), transaction.getType());
        assertEquals(transactionDTO.getAmount(), transaction.getAmount());
        assertEquals(transactionDTO.getDate(), transaction.getDate());
        assertEquals(transactionDTO.getStatus(), transaction.getStatus());
        assertEquals(transactionDTO.getSourceAccount(), transaction.getSourceAccount());
        assertEquals(transactionDTO.getDestinationAccount(), transaction.getDestinationAccount());
    }

    @Test
    void testToDTO_NullTransaction() {
        TransactionDTO transactionDTO = mapper.toDTO(null);
        assertNull(transactionDTO);
    }

    @Test
    void testToEntity_NullTransactionDTO() {
        Transaction transaction = mapper.toEntity(null);
        assertNull(transaction);
    }
}