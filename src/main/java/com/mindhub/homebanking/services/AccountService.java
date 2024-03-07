package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();

    List<AccountDTO> getAllAccountsDTO();

    List<Account> getAllAccountsByAccountHolder(Client AccountHolder);

    List<AccountDTO> getAllAccountsDTOByAccountHolder(Client AccountHolder);

    Boolean getAccountByNumberAndAccountHolder(String number, Client accountHolder);

    Account getAccountByNumber(String number);

    void saveAccount(Account account);


}
