package com.xyzbank.mstransactions.mstransactions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//DTO para encapsular los datos de las transacciones expuestos al cliente.

@Getter
@Setter
@AllArgsConstructor
public class TransactionDTO {
    private String id;
    private String type;
    private Double amount;
    private LocalDateTime date;
    private String status;
}
