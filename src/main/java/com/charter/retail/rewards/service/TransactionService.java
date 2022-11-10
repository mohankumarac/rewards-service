package com.charter.retail.rewards.service;

import com.charter.retail.rewards.exception.ResourceNotFoundException;
import com.charter.retail.rewards.model.Transaction;
import com.charter.retail.rewards.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    
    private TransactionRepository transactionRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAll() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionRepository.findAll().forEach(transaction -> {
            Transaction model = new Transaction();
            modelMapper.map(transaction, model);
            transactionList.add(model);
        });
        return transactionList;
    }

    public Transaction findById(Long transactionId) throws ResourceNotFoundException {
        Transaction model = new Transaction();
        com.charter.retail.rewards.domain.Transaction savedTransaction = getSavedTransaction(transactionId);
        modelMapper.map(savedTransaction, model);
        return model;
    }

    private com.charter.retail.rewards.domain.Transaction getSavedTransaction(Long transactionId) throws ResourceNotFoundException {
        Optional<com.charter.retail.rewards.domain.Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (!optionalTransaction.isPresent()) {
            throw new ResourceNotFoundException("Transaction not found for this id :: " + transactionId);
        }
        com.charter.retail.rewards.domain.Transaction savedTransaction = optionalTransaction.get();
        return savedTransaction;
    }

    public Long save(Transaction Transaction) {
        com.charter.retail.rewards.domain.Transaction TransactionDomain = new com.charter.retail.rewards.domain.Transaction();
        modelMapper.map(Transaction, TransactionDomain);
        return transactionRepository.save(TransactionDomain).getTransactionId();
    }

    public Long update(Long TransactionId, Transaction Transaction) throws ResourceNotFoundException {
        com.charter.retail.rewards.domain.Transaction savedTransaction = getSavedTransaction(TransactionId);
        modelMapper.map(Transaction, savedTransaction);
        return transactionRepository.save(savedTransaction).getTransactionId();
    }
}
