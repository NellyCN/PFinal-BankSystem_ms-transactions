package com.xyzbank.mstransactions.mstransactions.model;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId; //
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Builder
@Document(collection="transactions")
public class Transaction {

    @BsonId
    private String id;
    private String type;
    private Double amount;
    private LocalDateTime date;
    private String sourceAccount;
    private String destinationAccount;
}
