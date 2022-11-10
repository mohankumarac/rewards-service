package com.charter.retail.rewards.model;

import lombok.Data;

@Data
public class TransactionResponseDto {

    private Long transactionId;

    public TransactionResponseDto(Long transactionId) {
        this.transactionId = transactionId;
    }
}
