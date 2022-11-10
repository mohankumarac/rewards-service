package com.charter.retail.rewards.domain;

import lombok.Data;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(
        name = "TRANSACTION"
)
public class Transaction {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "TRANSACTION_ID"
    )
    private Long transactionId;
    @Column(
            name = "CUSTOMER_ID"
    )
    private Long customerId;
    @Column(
            name = "TRANSACTION_DATE"
    )
    private Timestamp transactionDate;
    @Column(
            name = "AMOUNT"
    )
    private double transactionAmount;
}