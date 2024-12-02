package com.xyzbank.mstransactions.mstransactions.mapper;

import com.xyzbank.mstransactions.mstransactions.dto.TransactionDTO;
import com.xyzbank.mstransactions.mstransactions.model.Transaction;

public interface TransactionMapper {
    TransactionDTO toDTO(Transaction transaction);
    Transaction toEntity(TransactionDTO transactionDTO);
}
