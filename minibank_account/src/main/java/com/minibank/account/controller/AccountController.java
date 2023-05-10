package com.minibank.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Operation(summary = "계좌조회", description = "계좌조회")
    @GetMapping("/rest/v0.8/{acntNo}")
    public Account retrieveAccount(@PathVariable(name = "acntNo") String acntNo) throws Exception{
        return accountService.retrieveAccount(acntNo);
    }

    @Operation(summary = "계좌등록", description = "계좌등록")
    @PostMapping("/rest/v0.8")
    public Integer createAccount(@RequestBody Account account) throws Exception{
    	return accountService.createAccount(account);
    }

    @Operation(summary = "계좌목록조회", description = "계좌목록조회")
    @GetMapping("/list/rest/v0.8/{cstmId}")
    public List<Account> retrieveAccountList(@PathVariable(name = "cstmId") String cstmId) throws Exception{
        List<Account> accountList =  accountService.retrieveAccountList(cstmId);
        return accountList;
    }
}
