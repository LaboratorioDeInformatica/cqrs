package com.springbank.banckacc.query.api.handlers;

import com.springbank.banckacc.core.models.BankAccount;
import com.springbank.banckacc.query.api.dto.AccountLookupResponse;
import com.springbank.banckacc.query.api.dto.EqualityType;
import com.springbank.banckacc.query.api.queries.FindAccountByHolderQuery;
import com.springbank.banckacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.banckacc.query.api.queries.FindAccountsWithBalanceQuery;
import com.springbank.banckacc.query.api.queries.FindAllAccountsQuery;
import com.springbank.banckacc.query.api.repository.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountQueryHandlerImpl implements AccountQueryHandler {

    private final AccountRepository accountRepository;

    public AccountQueryHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountById(FindAccountByIdQuery query) {
        Optional<BankAccount> bankAccount = accountRepository.findById(query.getId());
        AccountLookupResponse response = bankAccount.isPresent() ? new AccountLookupResponse("Bank Account Successfully Returned!", bankAccount.get())
                : new AccountLookupResponse("No Bank Account Found for ID -" + query.getId());
        return response;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountByHolderId(FindAccountByHolderQuery query) {
        Optional<BankAccount> bankAccount = accountRepository.findByAccountHolderId(query.getAccountHolderId());
        AccountLookupResponse response = bankAccount.isPresent() ? new AccountLookupResponse("Bank Account Successfully Returned!", bankAccount.get())
                : new AccountLookupResponse("No Bank Account Found for Holder ID -" + query.getAccountHolderId());
        return response;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAllAccounts(FindAllAccountsQuery query) {
        Iterable<BankAccount> bankAccountIterator = accountRepository.findAll();
        if(!bankAccountIterator.iterator().hasNext()) return new AccountLookupResponse("No bank Accounts were Found!");

        ArrayList<BankAccount> bankAccounts = new ArrayList<>();
        bankAccountIterator.forEach(i-> bankAccounts.add(i));
        int count = bankAccounts.size();
        return new AccountLookupResponse("Successfully Returned"+count+"bank Account(s)!", bankAccounts);

    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountsWithBalance(FindAccountsWithBalanceQuery query) {
        List<BankAccount> bankAccounts = query.getEqualityType() == EqualityType.GREATER_THAN
                ? accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());
        AccountLookupResponse response = bankAccounts != null && bankAccounts.size() > 0
                ? new AccountLookupResponse("Successfully Returned" + bankAccounts.size() + "Bank Account(s)", bankAccounts)
                : new AccountLookupResponse("No bank Accounts were Found!");
        return response;
    }
}
