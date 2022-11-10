package com.charter.retail.rewards.repository;

import com.charter.retail.rewards.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
