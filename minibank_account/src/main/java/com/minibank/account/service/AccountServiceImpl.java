package com.minibank.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minibank.account.domain.entity.Account;
import com.minibank.account.domain.entity.TransactionHistory;
import com.minibank.account.domain.repository.AccountRepository;
import com.minibank.account.exception.BusinessException;

import com.minibank.account.rest.customer.CustomerFeignClient;
import com.minibank.account.rest.customer.entity.Customer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired private AccountRepository accountRepository;

	//@Autowired private CustomerRestClient customerRestClient;
	@Autowired private CustomerFeignClient customerFeignClient;

    @Override
    public Account retrieveAccount(String acntNo) throws Exception {
    	Account account = accountRepository.selectAccount(Account.ofAcntNo(acntNo));

        if (account == null)
        	throw new BusinessException("존재하지않는 계좌번호입니다.");

        return account;
    }

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
        //Customer customer = customerComposite.retrieveCustomer(account.getCstmId());
        
        Customer customer = customerFeignClient.retrieveCustomer(account.getCstmId());
       
        account.setCstmNm(customer.getCstmNm());
        
        // 3) 계좌 생성
        result = accountRepository.insertAccount(account);
        
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAcntNo(account.getAcntNo());
        transactionHistory.setSeq(this.retrieveMaxSeq(account.getAcntNo())+1);
        transactionHistory.setDivCd("D");  // D : Deposit , W : Withdrawal
        transactionHistory.setStsCd("1");
        transactionHistory.setTrnsAmt(account.getAcntBlnc());
        transactionHistory.setAcntBlnc(account.getAcntBlnc());
        transactionHistory.setTrnsBrnch("마곡본점");

        // 3-1) 계좌 생성 금액을 입금 처리
        this.createTransactionHistory(transactionHistory);
        
        // 4) 계좌 정보 message send
        return result;
    }

    @Override //계좌목록 조회
    public List<Account> retrieveAccountList(String cstmId) throws Exception {
		return accountRepository.selectAccountList(Account.ofCstmId(cstmId));
    }

    @Override //계좌잔액 조회
	public Long retrieveAccountBalance(String acntNo) throws Exception {

		TransactionHistory transactionHistory = TransactionHistory.builder()
				.acntNo(acntNo).build();

		Long balance = accountRepository.selectCurrentAccountBalance(transactionHistory);

		if (balance == null)
			return 0L;
		else
			return balance;
	}

    @Override //거래내역 생성
    @Transactional(rollbackFor = Exception.class)
    public int createTransactionHistory(TransactionHistory transactionHistory) throws Exception {
    	return accountRepository.insertTransactionHistoryData(transactionHistory);
    }

    @Override //거래내역 목록 조회
    public List<TransactionHistory> retrieveTransactionHistoryList(String acntNo) throws Exception {
    	return accountRepository.selectTransactionHistoryList(TransactionHistory.ofAcntNo(acntNo));
    }

    @Override
    public int retrieveMaxSeq(String acntNo) throws Exception {
        return accountRepository.selectMaxSeq(TransactionHistory.ofAcntNo(acntNo));
    }
}
