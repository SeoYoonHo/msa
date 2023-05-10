package com.minibank.account.rest.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.minibank.account.rest.customer.entity.Customer;

import org.springframework.cloud.openfeign.FallbackFactory;

@FeignClient(name = "minibank-customer", fallbackFactory = CustomerFeignClientFallbackFactory.class)
public interface CustomerFeignClient {
	    
	/* TODO : Annotation 추가 */
	
    Customer retrieveCustomer(@PathVariable("cstmId") String cstmId) throws Exception;
}

@Component
class CustomerFeignClientFallbackFactory implements FallbackFactory<CustomerFeignClient>{
	@Override
	public CustomerFeignClient create(Throwable t) {
		return new CustomerFeignClient() {
			private final Logger LOGGER = LoggerFactory.getLogger(CustomerFeignClientFallbackFactory.class);
			
			@Override
			public Customer retrieveCustomer(String cstmId) throws Exception {
				// 외부 통신에러시 필요한 후속 조치를 여기서 설정 가능합니다.
		    	String msg = "feignClient를 이용하여 " + cstmId + " 고객정보 조회 서비스 호출에 문제가 있습니다.";
				LOGGER.error(msg, t);
				throw new Exception();
			}

		};
	}
}