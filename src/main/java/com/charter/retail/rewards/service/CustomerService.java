package com.charter.retail.rewards.service;

import com.charter.retail.rewards.exception.ResourceNotFoundException;
import com.charter.retail.rewards.model.Customer;
import com.charter.retail.rewards.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        customerRepository.findAll().forEach(customer -> {
            Customer model = new Customer();
            modelMapper.map(customer, model);
            customerList.add(model);
        });
        return customerList;
    }

    public Customer findById(Long customerId) throws ResourceNotFoundException {
        Customer model = new Customer();
        com.charter.retail.rewards.domain.Customer savedCustomer = getSavedCustomer(customerId);
        modelMapper.map(savedCustomer, model);
        return model;
    }

    private com.charter.retail.rewards.domain.Customer getSavedCustomer(Long customerId) throws ResourceNotFoundException {
        Optional<com.charter.retail.rewards.domain.Customer> optionalCustomer = customerRepository.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            throw new ResourceNotFoundException("Customer not found for this id :: " + customerId);
        }
        com.charter.retail.rewards.domain.Customer savedCustomer = optionalCustomer.get();
        return savedCustomer;
    }

    public Long save(Customer customer) {
        com.charter.retail.rewards.domain.Customer customerDomain = new com.charter.retail.rewards.domain.Customer();
        modelMapper.map(customer, customerDomain);
        return customerRepository.save(customerDomain).getCustomerId();
    }

    public Long update(Long customerId, Customer customer) throws ResourceNotFoundException {
        com.charter.retail.rewards.domain.Customer savedCustomer = getSavedCustomer(customerId);
        modelMapper.map(customer, savedCustomer);
        return customerRepository.save(savedCustomer).getCustomerId();
    }
}
