package com.minibank.account.rest.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.minibank.account.rest.customer.entity.Customer;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Component("customerRestClient")
public class CustomerRestClient {

    private static Logger LOGGER = LoggerFactory.getLogger(CustomerRestClient.class);
	
    @Value("${customer.api.url}")
    private String CUSTOMER_API_URL;
    
    //TODO: RestTemplate에 Spring Cloud Loadbalancer 적용
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    
    @Autowired
    @Lazy
    RestTemplate restTemplate;
    
    @CircuitBreaker(name = "customCircuitBreaker", fallbackMethod = "fallbackRetrieveCustomer")
	public Customer retrieveCustomer(String cstmId) throws Exception {
        String apiUrl =  "/rest/v0.8/{cstmId}";
        return this.restTemplate.getForObject(CUSTOMER_API_URL + apiUrl, Customer.class, cstmId);
	}
    
    public Customer fallbackRetrieveCustomer(String cstmId, Throwable t) throws Exception {
    	String msg = "restTemplate를 이용하여 " + cstmId + "고객정보 조회 서비스 호출에 문제가 있습니다.";
    	LOGGER.error(msg, t);
    	throw new Exception();
    }
}