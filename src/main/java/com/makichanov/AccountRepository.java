package com.makichanov;

import java.math.BigDecimal;

public interface AccountRepository {

    Account insertAccount(Account account);

    Account getAccount(String account);

    BigDecimal getBalanceByAccountNumber(String account);
}
