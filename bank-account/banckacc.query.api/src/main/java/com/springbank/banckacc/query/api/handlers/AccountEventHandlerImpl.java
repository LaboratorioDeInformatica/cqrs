package com.springbank.banckacc.query.api.handlers;

import com.springbank.banckacc.core.events.AccountClosedEvent;
import com.springbank.banckacc.core.events.AccountOpenedEvent;
import com.springbank.banckacc.core.events.FoundsDepositedEvent;
import com.springbank.banckacc.core.events.FoundsWithdrawnEvent;
import com.springbank.banckacc.core.models.BankAccount;
import com.springbank.banckacc.query.api.repository.AccountRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ProcessingGroup("bankaccount-group")
public class AccountEventHandlerImpl implements AccountEventHandler {

    private final AccountRepository accountRepository;

    public AccountEventHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @EventHandler
    @Override
    public void on(AccountOpenedEvent event) {
        BankAccount account = BankAccount.builder()
                .id(event.getId())
                .accountHolderId(event.getAccountHolderId())
                .creationDate(event.getCreationDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();
        accountRepository.save(account);
    }

    @EventHandler
    @Override
    public void on(FoundsDepositedEvent event) {
        Optional<BankAccount> bankAccount = accountRepository.findById(event.getId());

        if(!bankAccount.isPresent()) return;

        bankAccount.get().setBalance(event.getBalance());
        accountRepository.save(bankAccount.get());
    }

    @EventHandler
    @Override
    public void on(FoundsWithdrawnEvent event) {
        Optional<BankAccount> bankAccount = accountRepository.findById(event.getId());

        if(!bankAccount.isPresent()) return;

        bankAccount.get().setBalance(event.getBalance());
        accountRepository.save(bankAccount.get());
    }

    @EventHandler
    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
