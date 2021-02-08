package com.springbank.banckacc.query.api.handlers;

import com.springbank.banckacc.query.api.dto.AccountLookupResponse;
import com.springbank.banckacc.query.api.queries.FindAccountByHolderQuery;
import com.springbank.banckacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.banckacc.query.api.queries.FindAccountsWithBalanceQuery;
import com.springbank.banckacc.query.api.queries.FindAllAccountsQuery;

public interface AccountQueryHandler {
    AccountLookupResponse findAccountById(FindAccountByIdQuery query);
    AccountLookupResponse findAccountByHolderId(FindAccountByHolderQuery query);
    AccountLookupResponse findAllAccounts(FindAllAccountsQuery query);
    AccountLookupResponse findAccountsWithBalance(FindAccountsWithBalanceQuery query);

}
