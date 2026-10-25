package com.makichanov;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepositoryImpl implements AccountRepository {

    private ConcurrentHashMap<String, Account> accountMap = new ConcurrentHashMap<>();

    /**
     * Inserts a new account into the repository.
     *
     * @param account The account to be inserted.
     * @return The inserted account.
     */
    @Override
    public Account insertAccount(Account account) {
        this.accountMap.put(account.getAccountNumber(), account);
        return account;
    }

    @Override
    public Account getAccount(String account) {
        return accountMap.get(account);
    }

    @Override
    public BigDecimal getBalanceByAccountNumber(String account) {
        return accountMap.get(account).getBalance();
    }
}
