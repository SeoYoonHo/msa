package com.minibank.inquiry.subscriber;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.minibank.inquiry.domain.entity.Customer;
import com.minibank.inquiry.exception.SystemException;
import com.minibank.inquiry.service.InquiryService;

@Component
public class CustomerConsumer {
	
	@Resource(name = "inquiryService")
    private InquiryService inquiryService;
	private final Logger LOGGER = LoggerFactory.getLogger(CustomerConsumer.class);
	
	/**
	 * 고객 데이터 생성 및 고객 이력 데이터 생성
	 * @param customer
	 * @throws SystemException
	 */
	// TODO (4) creatingCustomerListener method 구현
	
    public void creatingCustomerListener(Customer customer, Acknowledgment ack) {
    	//
    }
    
}
