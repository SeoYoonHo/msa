package com.minibank.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minibank.customer.domain.entity.Customer;
import com.minibank.customer.domain.repository.CustomerRepository;
import com.minibank.customer.exception.BusinessException;
import com.minibank.customer.publisher.CustomerProducer;
import com.minibank.customer.rest.account.AccountFeignClient;
import com.minibank.customer.rest.account.entity.Account;
import com.minibank.customer.rest.transfer.TransferFeignClient;
import com.minibank.customer.rest.transfer.entity.TransferLimit;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerProducer customerProducer;

    @Autowired
    TransferFeignClient transferFeignClient;

    @Autowired
    AccountFeignClient accountFeignClient;

    /**
     * @Transactional 어노테이션은 RuntimeException을 상속받은 Exception이 throws 될 때 작동
     * Exception 혹은 사용자 에러는 @Transactional(rollbackFor = {Exception.class}) 처럼 명시
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createCustomer(Customer customer) throws Exception {
        String cstmId = customer.getCstmId();
        int result = 0;

        if (existsCustomerId(cstmId))
            throw new BusinessException("이미 존재하는 아이디입니다.");
        
        // 고객 정보 저장
        customerRepository.insertCustomer(customer);
      
        // feign을 이용한 통신시 에러 발생하면 RuntimeException을 상속받은 SystemException throw
        // 따라서, Transaction Rollback 발생, 또한 Feign은 Hystrix도 연동되어 있음.
        transferFeignClient.createTransferLimit(
                                TransferLimit.builder()
                                    .cstmId(cstmId)
                                    .oneDyTrnfLmt(new Long(500000000))
                                    .oneTmTrnfLmt(new Long(50000000))
                                    .build());
        
        // 카프카 전송은 모든 트랜잭션 처리가 완료된 후에 수행, 카프카 역시 SystemException throw
        // 생각해볼 문제, insertCustomer OK, createTransferLimit OK, 
        // but sendCreatingCustomerMessage NOK인 경우엔 어떻게 하지?
        customerProducer.sendCreatingCustomerMessage(customer);
            
        return result;
    }

    @Override
    public Customer retrieveCustomer(String cstmId) throws Exception {
        Customer customer = null;
  
        if (!existsCustomerId(cstmId)) 
            throw new BusinessException("존재하지 않는 아이디입니다.");

        customer = customerRepository.selectCustomer(Customer.builder().cstmId(cstmId).build());

        if (customer == null) 
            throw new BusinessException("고객 데이터를 조회하지 못했습니다.");
        

        return customer;
    }

    @Override
    public Customer retrieveCustomerDetail(String cstmId) throws Exception {
        Customer customer = null;
        
        if (!existsCustomerId(cstmId)) 
            throw new BusinessException("존재하지 않는 아이디입니다.");

        // 고개 데이터 조회
        customer = customerRepository.selectCustomer(Customer.builder().cstmId(cstmId).build());
        if (customer == null) 
            throw new BusinessException("고객 데이터를 조회하지 못했습니다.");

        // 이체 한도 조회
        TransferLimit transferLimit = transferFeignClient.retrieveTransferLimit(cstmId);
        if (transferLimit == null) 
            throw new BusinessException("이체 한도 정보를 조회하지 못했습니다.");

        customer.setOneDyTrnfLmt(transferLimit.getOneDyTrnfLmt());
        customer.setOneTmTrnfLmt(transferLimit.getOneTmTrnfLmt());

        // 계좌 목록 조회
        List<Account> accountList = accountFeignClient.retrieveAccountList(cstmId);
        if (accountList == null) 
            throw new BusinessException(cstmId + "님의 계좌 목록 정보를 조회하지 못했습니다.");

        customer.addAllAccounts(accountList);
            
        return customer;
    }

    @Override
    public boolean existsCustomerId(String cstmId) throws Exception {
        boolean ret = false;
        
        // 이미 존재하는 아이디라면 true 리턴
        if(customerRepository.existsCustomer(Customer.builder().cstmId(cstmId).build()) > 0) 
            ret = true;
          
        return ret;
    }

    @Override
    public List<Customer> retrieveCustomerList(String cstmId,String cstmNm,String acntNo) throws Exception {
    	List<Customer> customerList = null;
    	Customer customer = Customer.builder().cstmId(cstmId).cstmNm(cstmNm).build();
    	
    	// 계좌번호 input이 있을 경우, 해당 계좌번호를 가진 고객ID를 확보하기 위해 Account 서비스에서 조회
    	if( acntNo != null && !"".equals(acntNo.trim()) ) {
    		Account account = accountFeignClient.retrieveAccount(acntNo);
    		
    		if(account != null && account.getCstmId()!= null && !"".equals(account.getCstmId())) {
    			customer.setCstmId(account.getCstmId());
    		}
    	}
    	
    	// 고객목록 조회
    	customerList = customerRepository.selectCustomerList( customer );
    	
    	for(Customer customerItem : customerList) {
        	
        	// 이체 한도 조회
        	TransferLimit transferLimit = transferFeignClient.retrieveTransferLimit(customerItem.getCstmId());

        	customerItem.setOneDyTrnfLmt(transferLimit.getOneDyTrnfLmt());
        	customerItem.setOneTmTrnfLmt(transferLimit.getOneTmTrnfLmt());
        	
        	// 계좌목록을 조회하여 customer에 add
        	customerItem.addAllAccounts(accountFeignClient.retrieveAccountList(customerItem.getCstmId()));
    		
    	}
    	
    	return customerList;
    }
       
    @Override
    @Transactional(rollbackFor = Exception.class)
	public int updateCustomer(Customer customer) throws Exception {
    	String cstmId = customer.getCstmId();
    	int result = 0;

    	if (!existsCustomerId(cstmId)) 
    		throw new BusinessException("존재하지 않는 아이디입니다.");
           
    	// 고객 정보 수정
    	result = customerRepository.updateCustomer(customer);
        return result;
    }
}