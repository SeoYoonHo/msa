package com.minibank.account.rest.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.minibank.account.rest.customer.entity.Customer;

@Component("customerRestClient")
public class CustomerRestClient {

    private static Logger LOGGER = LoggerFactory.getLogger(CustomerRestClient.class);
	
    @Value("${customer.api.url}")
    private String CUSTOMER_API_URL;
    
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    
    @Autowired
    @Lazy
    RestTemplate restTemplate;
    
	public Customer retrieveCustomer(String cstmId) throws Exception {
        String apiUrl =  "/rest/v0.8/{cstmId}";
        /* TODO : 고객기본조회 API 호출 후 결과 return */
	}

}
