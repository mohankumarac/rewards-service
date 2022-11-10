package com.charter.retail.rewards.controller;

import com.charter.retail.rewards.exception.ResourceNotFoundException;
import com.charter.retail.rewards.model.Customer;
import com.charter.retail.rewards.model.CustomerResponseDto;
import com.charter.retail.rewards.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/customers")
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable(value = "id") Long customerId)
            throws ResourceNotFoundException {
       return customerService.findById(customerId);
    }

    @PostMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(new CustomerResponseDto(customerService.save(customer)));
    }

    @PutMapping(value = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable(value = "id") Long customerId,
                                                              @RequestBody Customer customer)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(new CustomerResponseDto(customerService.update(customerId, customer)));
    }
}
