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
public class TransferConsumer {
	
	@Resource(name = "inquiryService")
    private InquiryService inquiryService;
	private final Logger LOGGER = LoggerFactory.getLogger(TransferConsumer.class);
	
    /**
     * 이체 정보를  업데이트 합니다.
     * @param customer
     * @throws SystemException
     */
	// TODO (5) updatingTransferLimitListener method 구현
	
    public void updatingTransferLimitListener(Customer customer, Acknowledgment ack) {
    	//
    }
}
