package com.mindhub.homebanking.services.implService;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<AccountDTO> getAllAccountsDTO() {
        return getAllAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<Account> getAllAccountsByAccountHolder(Client AccountHolder) {
        return AccountHolder.getAccountSet().stream().toList();
    }

    @Override
    public List<AccountDTO> getAllAccountsDTOByAccountHolder(Client AccountHolder) {
        return getAllAccountsByAccountHolder(AccountHolder).stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public Boolean getAccountByNumberAndAccountHolder(String number, Client accountHolder) {
        return getAllAccountsByAccountHolder(accountHolder).stream().anyMatch(account -> account.getNumber().equals(number));
    }

    @Override
    public Account getAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}

