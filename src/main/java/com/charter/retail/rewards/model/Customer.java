package com.charter.retail.rewards.model;

import lombok.Data;

@Data
public class Customer {

    private String customerName;
    private String dateOfBirth;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String email;
    private String phoneNumber;
}
