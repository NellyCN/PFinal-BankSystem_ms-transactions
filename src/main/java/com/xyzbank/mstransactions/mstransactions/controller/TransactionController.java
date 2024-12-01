package com.xyzbank.mstransactions.mstransactions.controller;

import com.xyzbank.mstransactions.mstransactions.model.Transaction;
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

//    @Autowired
//    public TransactionController(TransactionService transactionService) {
//        this.transactionService = transactionService;
//    }

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Transaction> deposit(@RequestBody Transaction transaction) {
        return transactionService.deposit(transaction);     //Dependency Injection (DI): TransactionService se inyecta en el controlador.

    }

    @PostMapping("/withdrawal")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Transaction> withdrawal(@RequestBody Transaction transaction) {
        return transactionService.withdrawal(transaction);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Transaction> transfer(@RequestBody Transaction transaction) {
        return transactionService.transfer(transaction);
    }
    @GetMapping("/history")
    public Flux<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions(); // Llama al servicio para obtener todas las transacciones
    }
    @GetMapping("/history/{accountId}")
    public Flux<Transaction> getAllTransactionsAccount(@PathVariable String accountId) {
        return transactionService.getAllTransactionsAccount(accountId); // Llama al servicio para obtener todas las transacciones
    }

}