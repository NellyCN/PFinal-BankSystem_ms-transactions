package com.xyzbank.mstransactions.mstransactions.repository;

import com.xyzbank.mstransactions.mstransactions.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String>{

}
