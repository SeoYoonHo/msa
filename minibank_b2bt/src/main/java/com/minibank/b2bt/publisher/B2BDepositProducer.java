package com.minibank.b2bt.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.minibank.b2bt.domain.TransferHistory;

@Component
public class B2BDepositProducer {
	
    private final Logger LOGGER = LoggerFactory.getLogger(B2BDepositProducer.class);
    
    @Autowired
    private KafkaTemplate<String, TransferHistory> b2bDepositKafkaTemplate;

    @Value(value = "${b2b.deposit.topic.name}")
    private String b2bDepositTopicName;

    public void sendB2BDepositMessage(TransferHistory transferResult) {
        ListenableFuture<SendResult<String, TransferHistory>> future = b2bDepositKafkaTemplate.send(b2bDepositTopicName, transferResult);

        future.addCallback(new ListenableFutureCallback<SendResult<String, TransferHistory>>() {
            @Override
            public void onSuccess(SendResult<String, TransferHistory> result) {
                TransferHistory g = result.getProducerRecord().value();
                LOGGER.info("Sent b2bDeposit message=[" + g.getDpstAcntNo() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                // needed to do compensation transaction.
            	LOGGER.error( "Unable to send message=[" + transferResult.getWthdAcntNo() + "] due to : " + ex.getMessage(),ex);
            } 
        });
    }
}
