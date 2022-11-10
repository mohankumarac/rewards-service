package com.charter.retail.rewards.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Transaction {
    private Long customerId;
    private Timestamp transactionDate;
    private double transactionAmount;
}
