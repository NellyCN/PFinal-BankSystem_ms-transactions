package com.xyzbank.mstransactions.mstransactions.mapper;


import com.xyzbank.mstransactions.mstransactions.dto.TransactionDTO;
import com.xyzbank.mstransactions.mstransactions.model.Transaction;
import org.springframework.stereotype.Component;

//Implementación manual del mapeador entre Transaction y TransactionDTO.

@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) return null;
        return new TransactionDTO(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getStatus()
        );
    }

    @Override
    public Transaction toEntity(TransactionDTO transactionDTO) {
        if (transactionDTO == null) return null;
        return Transaction.builder()
                .id(transactionDTO.getId())
                .type(transactionDTO.getType())
                .amount(transactionDTO.getAmount())
                .date(transactionDTO.getDate())
                .status(transactionDTO.getStatus())
                .build();
    }
}
