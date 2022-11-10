package com.charter.retail.rewards.model;

import lombok.Data;

@Data
public class Rewards {
    private long customerId;
    private long lastMonthRewardPoints;
    private long lastSecondMonthRewardPoints;
    private long lastThirdMonthRewardPoints;
    private long totalRewards;

    public Rewards() {
    }
}

