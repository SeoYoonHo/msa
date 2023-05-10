package com.minibank.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minibank.account.domain.entity.Account;
import com.minibank.account.domain.repository.AccountRepository;
import com.minibank.account.exception.BusinessException;
import com.minibank.account.rest.customer.CustomerRestClient;
import com.minibank.account.rest.customer.entity.Customer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired private AccountRepository accountRepository;
	@Autowired private CustomerRestClient customerRestClient;

    @Override //계좌번호 존재확인
    public boolean existsAccountNumber(String acntNo) throws Exception {
    	boolean ret = false;
    	
    	if (accountRepository.selectAccount(Account.ofAcntNo(acntNo)) != null)
    		ret = true;
    		
    	return ret;
    	
    }

    @Override //계좌 생성
    @Transactional(rollbackFor = Exception.class)
    public Integer createAccount(Account account) throws Exception {

        int result = 0;

        // 1) 계좌번호 중복 검증
        if(existsAccountNumber(account.getAcntNo()))
        	throw new BusinessException("존재하는 계좌번호입니다.");

        // 2) 고객정보 조회 (계좌테이블에 '고객이름' 저장을 위해)
        Customer customer = customerRestClient.retrieveCustomer(account.getCstmId());
        account.setCstmNm(customer.getCstmNm());
        
        // 3) 계좌 생성
        result = accountRepository.insertAccount(account);

        return result;
    }

}
