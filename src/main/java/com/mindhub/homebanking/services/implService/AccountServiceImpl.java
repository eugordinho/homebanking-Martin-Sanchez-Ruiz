package com.mindhub.homebanking.services.implService;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public List<Account> getAllAccounts() {
        return null;
    }

    @Override
    public List<AccountDTO> getAllAccountsDTO() {
        return null;
    }

    @Override
    public List<Account> getAllAccountsByAccountHolder(Client AccountHolder) {
        return null;
    }

    @Override
    public List<AccountDTO> getAllAccountsDTOByAccountHolder(Client AccountHolder) {
        return null;
    }

    @Override
    public Boolean getAccountByNumberAndAccountHolder(String number, Client accountHolder) {
        return null;
    }

    @Override
    public Account getAccountByNumber(String number) {
        return null;
    }

    @Override
    public void saveAccount(Account account) {

    }
}

