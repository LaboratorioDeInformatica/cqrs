package com.springbank.banckacc.query.api.controllers;

import com.springbank.banckacc.query.api.dto.AccountLookupResponse;
import com.springbank.banckacc.query.api.dto.EqualityType;
import com.springbank.banckacc.query.api.queries.FindAccountByHolderQuery;
import com.springbank.banckacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.banckacc.query.api.queries.FindAccountsWithBalanceQuery;
import com.springbank.banckacc.query.api.queries.FindAllAccountsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/v1/bankAccountLookup")
public class AccountLookUpController {
    private final QueryGateway queryGateway;

    public AccountLookUpController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAllAccounts(){
        try{
            FindAllAccountsQuery  query = new FindAllAccountsQuery();
            AccountLookupResponse response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();
            if(response == null || response.getAccounts() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            String safeErrorMessage ="Failed to complete get all accounts request";
            System.out.println(e.toString());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byId/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value = "id") String id){
        try{
            FindAccountByIdQuery query = new FindAccountByIdQuery(id);
            AccountLookupResponse response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();
            if(response == null || response.getAccounts() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            String safeErrorMessage ="Failed to complete get account by id request";
            System.out.println(e.toString());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byHolderId/{accountHolderId}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAccountByHolderId(@PathVariable(value = "accountHolderId") String accountHolderId){
        try{
            FindAccountByHolderQuery query = new FindAccountByHolderQuery(accountHolderId);
            AccountLookupResponse response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();
            if(response == null || response.getAccounts() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            String safeErrorMessage ="Failed to complete get account by holder id request";
            System.out.println(e.toString());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/withBalance/{equalityType}/{balance}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAccountWithBalance(@PathVariable(value = "equalityType") EqualityType equalityType, @PathVariable(value = "balance") double balance){
        try{
            FindAccountsWithBalanceQuery query = new FindAccountsWithBalanceQuery(equalityType, balance);
            AccountLookupResponse response = queryGateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();
            if(response == null || response.getAccounts() == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            String safeErrorMessage ="Failed to complete get account with balance request";
            System.out.println(e.toString());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
