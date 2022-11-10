package com.charter.retail.rewards.repository;

import com.charter.retail.rewards.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByCustomerIdAndTransactionDateBetween(Long var1, Timestamp var2, Timestamp var3);
}
