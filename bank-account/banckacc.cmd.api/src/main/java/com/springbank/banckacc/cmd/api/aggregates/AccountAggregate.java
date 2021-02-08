package com.springbank.banckacc.cmd.api.aggregates;

import com.springbank.banckacc.cmd.api.commands.CloseAccountCommand;
import com.springbank.banckacc.cmd.api.commands.DepositFoundsCommand;
import com.springbank.banckacc.cmd.api.commands.OpenAccountCommand;
import com.springbank.banckacc.cmd.api.commands.WithdrawFoundsCommand;
import com.springbank.banckacc.core.events.AccountClosedEvent;
import com.springbank.banckacc.core.events.AccountOpenedEvent;
import com.springbank.banckacc.core.events.FoundsDepositedEvent;
import com.springbank.banckacc.core.events.FoundsWithdrawnEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
@NoArgsConstructor
public class AccountAggregate {

    @AggregateIdentifier
    private String id;
    private String accountHolderId;
    private double balance;


    @CommandHandler
    public AccountAggregate(OpenAccountCommand command){
        AccountOpenedEvent event = AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolderId(command.getAccountHolderId())
                .accountType(command.getAccountType())
                .creationDate(new Date())
                .openingBalance(command.getOpeningBalance())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountOpenedEvent event){
        this.id = event.getId();
        this.accountHolderId = event.getAccountHolderId();
        this.balance = event.getOpeningBalance();
    }

    @CommandHandler
    public void handle(DepositFoundsCommand command){
        double amount = command.getAmount();
        FoundsDepositedEvent event = FoundsDepositedEvent.builder()
                .id(command.getId())
                .amount(amount)
                .balance(this.balance + amount)
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(FoundsDepositedEvent event){
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(WithdrawFoundsCommand command){
        double amount = command.getAmount();
        if(this.balance -amount < 0){
            throw new IllegalStateException("Withdrawn declined, insufficient founds!");
        }

        FoundsWithdrawnEvent event = FoundsWithdrawnEvent.builder()
                .id(command.getId())
                .amount(amount)
                .balance(this.balance - amount)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(FoundsWithdrawnEvent event){
        this.balance -= event.getAmount();
    }

    @CommandHandler
    public void handle(CloseAccountCommand command){
        AccountClosedEvent event = AccountClosedEvent.builder().id(command.getId()).build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountClosedEvent event){
        AggregateLifecycle.markDeleted();
    }
}
