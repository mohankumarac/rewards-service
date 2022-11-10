package com.charter.retail.rewards.controller;

import com.charter.retail.rewards.model.Transaction;
import com.charter.retail.rewards.exception.ResourceNotFoundException;
import com.charter.retail.rewards.model.TransactionResponseDto;
import com.charter.retail.rewards.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"v1/customers"})
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(value = "/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionService.findAll();
    }

    @GetMapping("/transactions/{id}")
    public Transaction getTransactionsById(@PathVariable(value = "id") Long transactionId)
            throws ResourceNotFoundException {
        return transactionService.findById(transactionId);
    }

    @PostMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseDto> createTransactions(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(new TransactionResponseDto(transactionService.save(transaction)));
    }

    @PutMapping(value = "/transactions/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseDto> updateTransactions(@PathVariable(value = "id") Long transactionId,
                                                              @RequestBody Transaction transaction)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(new TransactionResponseDto(transactionService.update(transactionId, transaction)));
    }
}
