package com.charter.retail.rewards.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(
        name = "CUSTOMER"
)
public class Customer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "CUSTOMER_ID"
    )
    private Long customerId;
    @Column(
            name = "CUSTOMER_NAME"
    )
    private String customerName;
    @Column(
            name = "DATE_OF_BIRTH"
    )
    private String dateOfBirth;
    @Column(
            name = "ADDRESS"
    )
    private String address;
    @Column(
            name = "CITY"
    )
    private String city;
    @Column(
            name = "STATE"
    )
    private String state;
    @Column(
            name = "ZIPCODE"
    )
    private String zipcode;
    @Column(
            name = "EMAIL"
    )
    private String email;
    @Column(
            name = "PHONE_NUMBER"
    )
    private String phoneNumber;

    public Customer() {
    }
}
