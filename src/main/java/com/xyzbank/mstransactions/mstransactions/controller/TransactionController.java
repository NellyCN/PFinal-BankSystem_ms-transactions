package com.xyzbank.mstransactions.mstransactions.controller;

import com.xyzbank.mstransactions.mstransactions.dto.TransactionDTO;
import com.xyzbank.mstransactions.mstransactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Controlador REST para manejar las transacciones

@RestController             // Indica al framework que esta clase se usará para recibir peticiones HTTP
@RequestMapping("/api/transactions/")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<TransactionDTO> deposit(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.deposit(transactionDTO);
    }

    @PostMapping("/withdrawal")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionDTO> withdrawal(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.withdrawal(transactionDTO);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionDTO> transfer(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.transfer(transactionDTO);
    }

    @GetMapping("/history")
    public Flux<TransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

}