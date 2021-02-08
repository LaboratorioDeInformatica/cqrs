package com.springbank.banckacc.query.api.handlers;

import com.springbank.banckacc.core.events.AccountClosedEvent;
import com.springbank.banckacc.core.events.AccountOpenedEvent;
import com.springbank.banckacc.core.events.FoundsDepositedEvent;
import com.springbank.banckacc.core.events.FoundsWithdrawnEvent;

public interface AccountEventHandler {
    void on(AccountOpenedEvent event);
    void on(FoundsDepositedEvent event);
    void on(FoundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
