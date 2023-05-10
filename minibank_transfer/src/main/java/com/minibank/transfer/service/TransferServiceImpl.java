package com.minibank.transfer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minibank.transfer.domain.entity.TransferHistory;
import com.minibank.transfer.domain.entity.TransferLimit;
import com.minibank.transfer.domain.repository.TransferRepository;
import com.minibank.transfer.exception.BusinessException;
import com.minibank.transfer.rest.account.AccountFeignClient;
import com.minibank.transfer.rest.customer.CustomerFeignClient;


@Service("transferService")
public class TransferServiceImpl implements TransferService {
	
    @Autowired
    TransferRepository transferRepository;
        
    @Autowired
    AccountFeignClient accountFeignClient;
    
    @Autowired
    CustomerFeignClient customerFeignClient;
    
    @Override
    public int createTransferHistory(TransferHistory transferHistory) throws Exception {
    	// 이체 내역 생성
    	return transferRepository.insertTransferHistory(transferHistory);
    }

    @Override
    public List<TransferHistory> retrieveTransferHistoryList(String cstmId) throws Exception {
        // 고객 존재여부 조회
        if(customerFeignClient.existsCustomerId(cstmId) == false)
        	throw new BusinessException("존재하지 않는 아이디입니다.");
        
    	TransferHistory transferHistory = new TransferHistory();
    	transferHistory.setCstmId(cstmId);
    	
       	// 이체 이력 조회
        return transferRepository.selectTransferHistoryList(transferHistory);
    }

    @Override
    public TransferLimit retrieveTransferLimit(String cstmId) throws Exception {
    	TransferLimit transferLimit = new TransferLimit();
    	transferLimit.setCstmId(cstmId);
        
        // 이체 한도 조회
    	return transferRepository.selectTransferLimit(transferLimit);
    }

    @Override
    public Long retrieveTotalTransferAmountPerDay(String cstmId) throws Exception {
    	TransferLimit transferLimit = new TransferLimit();
    	transferLimit.setCstmId(cstmId);
        
        return transferRepository.selectTotalTransferAmountPerDay(transferLimit);
        
    }
    
	@Override
	public TransferLimit retrieveEnableTransferLimit(String cstmId) throws Exception {
		TransferLimit transferLimit = retrieveTransferLimit(cstmId);
        if(transferLimit == null)
            throw new BusinessException("이체 한도 조회를 실패하였습니다.");
        else {
        	// 일일 이체 합계 금액 조회
            Long totalTransferAmountPerDay = retrieveTotalTransferAmountPerDay(cstmId);
            if(totalTransferAmountPerDay < 0)
                throw new BusinessException("일일 이체 합계 금액 조회를 실패하였습니다.");
            else {
                Long remaingOneDayTransferLimit = transferLimit.getOneDyTrnfLmt() - totalTransferAmountPerDay;
                transferLimit.setOneDyTrnfLmt(remaingOneDayTransferLimit);
                return transferLimit;
            }
        }
	}
	
	private int retrieveMaxSeq(String cstmId) throws Exception {
		TransferHistory transferHistory = new TransferHistory();
		transferHistory.setCstmId(cstmId);
		
		return transferRepository.selectMaxSeq(transferHistory);
	}
}
