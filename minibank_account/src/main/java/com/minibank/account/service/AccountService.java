package com.minibank.account.service;

import com.minibank.account.domain.entity.Account;

public interface AccountService {
    Integer createAccount(Account account) throws Exception;
    boolean existsAccountNumber(String acntNo) throws Exception;
}
