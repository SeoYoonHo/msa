package com.minibank.inquiry.subscriber;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.minibank.inquiry.domain.entity.Account;
import com.minibank.inquiry.exception.SystemException;
import com.minibank.inquiry.service.InquiryService;

@Component
public class AccountConsumer {
	@Resource(name = "inquiryService")
    private InquiryService inquiryService;
	private final Logger LOGGER = LoggerFactory.getLogger(AccountConsumer.class);

	/**
	 * 고객상세조회 : 계좌 데이터 등록
	 * @param account
	 * @param ack
	 */
	// TODO (2)createAccountListener method 구현
		
    public void creatingAccountListener(Account account, Acknowledgment ack) {
    	//
    }
    
    /**
     * 계좌 잔액 업데이트
     * @param account
     * @throws SystemException
     */
    // TODO (3)updatingAccountBalanceListener method 구현
    
    public void updatingAccountBalanceListener(Account account, Acknowledgment ack) {
    	//
    }
    
}
