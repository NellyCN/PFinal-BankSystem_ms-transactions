package com.xyzbank.mstransactions.mstransactions.controller;

import com.xyzbank.mstransactions.mstransactions.model.Transaction;
import com.xyzbank.mstransactions.mstransactions.service.TransactionService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public Mono<Transaction> deposit(@RequestBody Transaction transaction) {
        return transactionService.deposit(transaction);
    }

    @PostMapping("/withdrawal")
    public Mono<Transaction> withdrawal(@RequestBody Transaction transaction) {
        return transactionService.withdrawal(transaction);
    }

    @PostMapping("/transfer")
    public Mono<Transaction> transfer(@RequestBody Transaction transaction) {
        return transactionService.transfer(transaction);
    }

    @GetMapping("/transactions")
    public Flux<Transaction> viewTransactions(@RequestHeader Map<String, String> headers) {
        return transactionService.getAllTransactions();
    }



//    @GetMapping("/transactions/{id}")
//    public Mono<Transaction> viewTransaction(@RequestHeader Map<String, String> headers,
//                                       @PathVariable String id) {
//        return transactionService.getTransaction(id)
//                .doOnError(throwable -> System.out.println(throwable));
//    }
//    @DeleteMapping("/transactions/{id}")
//    public Mono<Void> deleteTransaction(@RequestHeader Map<String, String> headers, @PathVariable
//    String id) {
//        return transactionService.deleteTransaction(id);
//    }

}
