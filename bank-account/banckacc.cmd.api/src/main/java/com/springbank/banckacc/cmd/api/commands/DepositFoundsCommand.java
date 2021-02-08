package com.springbank.banckacc.cmd.api.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Min;

@Data
@Builder
public class DepositFoundsCommand {
    @TargetAggregateIdentifier
    private String id;
    @Min(value = 1, message = "the deposit amount must be greater than 0.")
    private double amount;
}
