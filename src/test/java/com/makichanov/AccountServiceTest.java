package com.makichanov;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService accountService = new AccountService(new AccountRepositoryImpl());

    @Test
    void successfulInitUserAccountTest() {
        BigDecimal deposit = new BigDecimal("2.34");
        Account expected = new Account();
        expected.setUserId(1L);
        expected.setLocked(false);
        expected.setBalance(deposit);

        Account actual = accountService.initUserAccount(1L, deposit);
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getBalance(), actual.getBalance());
        assertFalse(actual.isLocked());
        assertNotNull(actual.getAccountNumber());
    }

    @Test
    void negativeBalanceInitUserAccountTest() {
        BigDecimal deposit = new BigDecimal("-1.34");

        assertThrows(IllegalStateException.class, () -> accountService.initUserAccount(1L, deposit));
    }

    @Test
    void getBalanceTest() {
        BigDecimal deposit = new BigDecimal("2.34");
        Account expected = new Account();
        expected.setUserId(1L);
        expected.setLocked(false);
        expected.setBalance(deposit);

        Account actual = accountService.initUserAccount(1L, deposit);

        BigDecimal balance = accountService.getBalance(actual.getAccountNumber());
        assertEquals(deposit, balance);
    }

    @Test
    void noAccountGetBalanceTest() {
        BigDecimal balance = accountService.getBalance("qwewtgd");
        assertEquals(balance, BigDecimal.ZERO);
    }

    @Test
    void successfulDepositTest() {
        BigDecimal deposit = new BigDecimal("2.34");
        Account expected = new Account();
        expected.setUserId(1L);
        expected.setLocked(false);
        expected.setBalance(deposit);

        Account inserted = accountService.initUserAccount(1L, deposit);
        Account actual = accountService.deposit(inserted.getAccountNumber(), BigDecimal.TEN);
        BigDecimal expectedBalance = deposit.add(BigDecimal.TEN);
        assertEquals(expectedBalance, actual.getBalance());
    }

    @Test
    void successfulWithdrawTest() {
        BigDecimal deposit = new BigDecimal("12.34");
        Account expected = new Account();
        expected.setUserId(1L);
        expected.setLocked(false);
        expected.setBalance(deposit);

        Account inserted = accountService.initUserAccount(1L, deposit);
        Account actual = accountService.withdraw(inserted.getAccountNumber(), BigDecimal.TEN);
        BigDecimal expectedBalance = deposit.subtract(BigDecimal.TEN);
        assertEquals(expectedBalance, actual.getBalance());
    }
}