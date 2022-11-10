package com.charter.retail.rewards.controller;

import com.charter.retail.rewards.exception.ResourceNotFoundException;
import com.charter.retail.rewards.model.Rewards;
import com.charter.retail.rewards.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"v1/customers"})
public class RewardsController {
    private RewardsService rewardsService;

    @Autowired
    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping(value = {"/{customerId}/rewards"})
    public ResponseEntity<Rewards> getRewardsByCustomerId(@PathVariable("customerId") Long customerId)
            throws ResourceNotFoundException {
        return new ResponseEntity(rewardsService.getRewardsById(customerId), HttpStatus.OK);
    }

    @GetMapping(value = {"/rewards"})
    public ResponseEntity<List<Rewards>> getRewardsByEachCustomer()
            throws ResourceNotFoundException {
        return new ResponseEntity(rewardsService.getRewardsByEachCustomer(), HttpStatus.OK);
    }
}

