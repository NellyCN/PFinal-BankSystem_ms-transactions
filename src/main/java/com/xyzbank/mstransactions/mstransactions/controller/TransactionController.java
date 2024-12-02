package com.xyzbank.mstransactions.mstransactions.controller;

import com.xyzbank.mstransactions.mstransactions.dto.TransactionDTO;
import com.xyzbank.mstransactions.mstransactions.mapper.TransactionMapper;
import com.xyzbank.mstransactions.mstransactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController             // Indica al framework que esta clase se usará para recibir peticiones HTTP
@RequestMapping("/api/transactions/")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<TransactionDTO> deposit(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.deposit(transactionMapper.toEntity(transactionDTO))     //Dependency Injection (DI): TransactionService se inyecta en el controlador.
                .map(transactionMapper::toDTO);
    }

    @PostMapping("/withdrawal")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionDTO> withdrawal(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.withdrawal(transactionMapper.toEntity(transactionDTO))
                .map(transactionMapper::toDTO);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionDTO> transfer(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.transfer(transactionMapper.toEntity(transactionDTO))
                .map(transactionMapper::toDTO);
    }
    @GetMapping("/history")
    public Flux<TransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions()   // Llama al servicio para obtener todas las transacciones
                .map(transactionMapper::toDTO);
    }
    @GetMapping("/history/{accountId}")
    public Flux<TransactionDTO> getAllTransactionsAccount(@PathVariable String accountId) {
        return transactionService.getAllTransactionsAccount(accountId) // Llama al servicio para obtener todas las transacciones
                .map(transactionMapper::toDTO);
    }

}