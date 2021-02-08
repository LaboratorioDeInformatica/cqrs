package com.springbank.banckacc.core.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoundsDepositedEvent {
    private String id;
    private double amount;
    private double balance;
}
