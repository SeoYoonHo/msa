package com.minibank.transfer.rest.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.minibank.transfer.exception.SystemException;

import org.springframework.cloud.openfeign.FallbackFactory;

@FeignClient(name = "minibank-customer",  
			 fallbackFactory = CustomerFeignClientFallbackFactory.class)
public interface CustomerFeignClient {

	@GetMapping("/minibank/customer/exists/rest/v0.8/{cstmId}")
	Boolean existsCustomerId(@PathVariable("cstmId") String cstmId);
	
}

@Component
class CustomerFeignClientFallbackFactory implements FallbackFactory<CustomerFeignClient> {
	@Override
	public CustomerFeignClient create(Throwable t) {
		return new CustomerFeignClient() {
			private final Logger LOGGER = LoggerFactory.getLogger(CustomerFeignClient.class);

			@Override
			public Boolean existsCustomerId(String cstmId) throws SystemException {				
				String msg = "고객 조회에 실패하였습니다.";
				LOGGER.error(msg, t);
				throw new SystemException(msg);
			}
		};
	}
}