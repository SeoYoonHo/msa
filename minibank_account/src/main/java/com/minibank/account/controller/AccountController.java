package com.minibank.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.minibank.account.domain.entity.Account;
import com.minibank.account.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
public class AccountController {

	@Autowired
    private AccountService accountService;

    @Operation(summary = "계좌등록", description = "계좌등록" )
    @PostMapping("/rest/v0.8")
    public Integer createAccount(@RequestBody Account account) throws Exception{
    	return accountService.createAccount(account);
    }
}
