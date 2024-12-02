package com.xyzbank.mstransactions.mstransactions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransactionDTO {
    private String id;
    private String type;
    private Double amount;
    private LocalDateTime date;
    private String status; // Incluye solo los campos necesarios
}
