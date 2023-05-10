package com.minibank.customer.rest.account;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.minibank.customer.rest.account.entity.Account;

import org.springframework.cloud.openfeign.FallbackFactory;

@FeignClient(name = "minibank-account", 
			 fallbackFactory = AccountFeignClientFallbackFactory.class)
public interface AccountFeignClient {

	@GetMapping("/minibank/account/list/rest/v0.8/{cstmId}")
	List<Account> retrieveAccountList(@PathVariable("cstmId") String cstmId) throws Exception;

}

@Component
class AccountFeignClientFallbackFactory implements FallbackFactory<AccountFeignClient> {
	@Override
	public AccountFeignClient create(Throwable t) {
		return new AccountFeignClient() {
			private final Logger LOGGER = LoggerFactory.getLogger(AccountFeignClientFallbackFactory.class);
			
			@Override
			public List<Account> retrieveAccountList(String cstmId) throws Exception {
				// 외부 통신에러시 필요한 후속 조치를 여기서 설정 가능합니다.
				String msg = "feignClient를 이용한 " + cstmId + " 고객의 계좌 서비스 호출에 문제가 있습니다.";
				LOGGER.error(msg, t);
				throw new Exception();
			}

		};
	}

}