package com.charter.retail.rewards.service;

import com.charter.retail.rewards.domain.Customer;
import com.charter.retail.rewards.domain.Transaction;
import com.charter.retail.rewards.exception.ResourceNotFoundException;
import com.charter.retail.rewards.model.Rewards;
import com.charter.retail.rewards.repository.CustomerRepository;
import com.charter.retail.rewards.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RewardsService {
    private CustomerRepository customerRepository;

    private TransactionRepository transactionRepository;

    public static final int daysInMonths = 30;
    public static int firstRewardLimit = 50;
    public static int secondRewardLimit = 100;

    @Autowired
    public RewardsService(CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    public Rewards getRewardsById(Long customerId) throws ResourceNotFoundException {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            throw new ResourceNotFoundException("Customer not found for this id :: " + customerId);
        } else {
            return getRewardsByCustomerId(customerId);
        }
    }

    public Rewards getRewardsByCustomerId(Long customerId) {
        Timestamp lastMonthTimestamp = this.getDateBasedOnOffSetDays(30);
        Timestamp lastSecondMonthTimestamp = this.getDateBasedOnOffSetDays(60);
        Timestamp lastThirdMonthTimestamp = this.getDateBasedOnOffSetDays(90);
        List<Transaction> lastMonthTransactions = this.transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, lastMonthTimestamp, Timestamp.from(Instant.now()));
        List<Transaction> lastSecondMonthTransactions = this.transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);
        List<Transaction> lastThirdMonthTransactions = this.transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, lastThirdMonthTimestamp, lastSecondMonthTimestamp);
        Long lastMonthRewardPoints = this.getRewardsPerMonth(lastMonthTransactions);
        Long lastSecondMonthRewardPoints = this.getRewardsPerMonth(lastSecondMonthTransactions);
        Long lastThirdMonthRewardPoints = this.getRewardsPerMonth(lastThirdMonthTransactions);
        Rewards customerRewards = new Rewards();
        customerRewards.setCustomerId(customerId);
        customerRewards.setLastMonthRewardPoints(lastMonthRewardPoints);
        customerRewards.setLastSecondMonthRewardPoints(lastSecondMonthRewardPoints);
        customerRewards.setLastThirdMonthRewardPoints(lastThirdMonthRewardPoints);
        customerRewards.setTotalRewards(lastMonthRewardPoints + lastSecondMonthRewardPoints + lastThirdMonthRewardPoints);
        return customerRewards;
    }

    private Long getRewardsPerMonth(List<Transaction> transactions) {
        return (Long)transactions.stream().map((transaction) -> {
            return this.calculateRewards(transaction);
        }).collect(Collectors.summingLong((r) -> {
            return r;
        }));
    }

    private Long calculateRewards(Transaction t) {
        if (t.getTransactionAmount() > firstRewardLimit && t.getTransactionAmount() <= secondRewardLimit) {
            return Math.round(t.getTransactionAmount() - firstRewardLimit);
        } else {
            return t.getTransactionAmount() > secondRewardLimit ? Math.round(t.getTransactionAmount() - secondRewardLimit) * 2L + (long)(secondRewardLimit - firstRewardLimit) : 0L;
        }
    }

    public Timestamp getDateBasedOnOffSetDays(int days) {
        return Timestamp.valueOf(LocalDateTime.now().minusDays((long)days));
    }

    public List<Rewards> getRewardsByEachCustomer() {
        List<Customer> customerList = this.customerRepository.findAll();
        List<Rewards> rewardsList = new ArrayList<>();
        customerList.forEach(customer -> {
            rewardsList.add(getRewardsByCustomerId(customer.getCustomerId()));
        });
        return rewardsList;
    }
}
