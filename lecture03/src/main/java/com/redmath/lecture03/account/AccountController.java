package com.redmath.lecture03.account;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/api/v1/accounts/{accountId}")
    public ResponseEntity<Account> get(@PathVariable("accountId") Long newsId) {
        Optional<Account> news = accountService.findById(newsId);
        if (news.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(news.get());
    }

    @GetMapping("/api/v1/accounts")
    public ResponseEntity<List<Account>> get(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                             @RequestParam(name = "page", defaultValue = "1000") Integer size) {
        return ResponseEntity.ok(accountService.findAll(page, size));
    }

//    @PostMapping("/api/v1/accounts")
//    public ResponseEntity<Account> create(@RequestBody Account account) {
//        account = accountService.create(account);
//        return ResponseEntity.created(URI.create("/api/v1/accounts/" + account.getAccountId())).body(account);
//    }
}
