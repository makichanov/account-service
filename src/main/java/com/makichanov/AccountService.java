package com.makichanov;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account initUserAccount(Long userId, BigDecimal depositAmount) {
        if (depositAmount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalStateException();

        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString().replace("-", ""));
        account.setBalance(depositAmount);
        account.setUserId(userId);
        return accountRepository.insertAccount(account);
    }

    public Account deposit(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.getAccount(accountNumber);
        if (account == null) throw new RuntimeException();
        if (amount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalStateException();

        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        return accountRepository.insertAccount(account);
    }

    public Account withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.getAccount(accountNumber);
        if (account == null) throw new RuntimeException();
        if (amount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalStateException();

        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        return accountRepository.insertAccount(account);
    }

    public BigDecimal getBalance(String account) {
        if (accountRepository.getAccount(account) == null) return BigDecimal.ZERO;
        return accountRepository.getBalanceByAccountNumber(account);
    }
}
