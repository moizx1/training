package com.redmath.lecture03.account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> findById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public List<Account> findAll(Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size > 1000) {
            size = 1000;
        }
        return accountRepository.findAll(PageRequest.of(page, size)).getContent();
    }

//    public Account create(Account account) {
//        account.setAccountId(System.currentTimeMillis());
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        account.setBalancey(username);
//        account.setCreatedAt(LocalDateTime.now());
//        return accountRepository.save(account);
//    }
}
