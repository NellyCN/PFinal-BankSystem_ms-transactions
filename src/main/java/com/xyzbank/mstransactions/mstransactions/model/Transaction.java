package com.xyzbank.mstransactions.mstransactions.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import org.bson.codecs.pojo.annotations.BsonId; //  identificador único (ID) pojo(representa un Doc MongoDB)
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(collection="transactions")    // Asocia esta clase con la colección "transactions"
public class Transaction {

    @BsonId
    private String id;  // Este campo se mapeará al campo _id en MongoDB

    private String type;
    private Double amount;
    private LocalDateTime date;
    private String sourceAccount;
    private String destinationAccount;

    // Constructor por defecto
    public Transaction() {
    }

    // Constructor
    public Transaction(String id, String type, Double amount, LocalDateTime date, String sourceAccount, String destinationAccount)
    {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
    }
}