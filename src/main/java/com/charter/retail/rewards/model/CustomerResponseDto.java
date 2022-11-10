package com.charter.retail.rewards.model;

import lombok.Data;

@Data
public class CustomerResponseDto {
    private Long customerId;

    public CustomerResponseDto(Long customerId) {
        this.customerId = customerId;
    }
}
