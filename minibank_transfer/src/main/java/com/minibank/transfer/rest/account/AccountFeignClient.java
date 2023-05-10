package com.minibank.transfer.rest.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.minibank.transfer.rest.account.entity.Account;
import com.minibank.transfer.rest.account.entity.TransactionHistory;
import com.minibank.transfer.rest.account.entity.TransactionResult;

import org.springframework.cloud.openfeign.FallbackFactory;

// TODO Remote Service와 REST 통신을 위한 Feign Client를 구현

public interface AccountFeignClient {

	TransactionResult withdraw(@RequestBody TransactionHistory transaction) throws Exception;
	TransactionResult deposit(@RequestBody TransactionHistory transaction) throws Exception;
	Account retrieveAccount(@PathVariable("acntNo") String acntNo) throws Exception;
	int cancelWithdraw(@RequestBody TransactionHistory transaction) throws Exception;	

}

@Component
class AccountFeignClientFallbackFactory implements FallbackFactory<AccountFeignClient> {
	@Override
	public AccountFeignClient create(Throwable t) {
		return new AccountFeignClient() {
			private final Logger LOGGER = LoggerFactory.getLogger(AccountFeignClient.class);

			@Override
			public TransactionResult withdraw(TransactionHistory transaction) throws Exception {				
				return null;
			}

			@Override
			public TransactionResult deposit(TransactionHistory transaction) throws Exception {				
				return null;
			}

			@Override
			public Account retrieveAccount(String acntNo) throws Exception {
				return null;
			}

			@Override
			public int cancelWithdraw(TransactionHistory transaction) throws Exception {
				return null;
			}
		};
	}
}