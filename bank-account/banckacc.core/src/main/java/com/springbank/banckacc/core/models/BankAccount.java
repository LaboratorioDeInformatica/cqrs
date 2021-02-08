package com.springbank.banckacc.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "bank_account")
public class BankAccount {
    @Id
    private String id;
    @Column(name = "account_holder_id")
    private String accountHolderId;
    @Column(name = "creation_date")
    private Date creationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;
    @Column(name = "balance")
    private double balance;
}
