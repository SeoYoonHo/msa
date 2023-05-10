package com.minibank.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minibank.customer.domain.entity.Customer;
import com.minibank.customer.domain.repository.CustomerRepository;
import com.minibank.customer.exception.BusinessException;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

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
    public boolean existsCustomerId(String cstmId) throws Exception {
        boolean ret = false;
        
        // 이미 존재하는 아이디라면 true 리턴
        if(customerRepository.existsCustomer(Customer.builder().cstmId(cstmId).build()) > 0) 
            ret = true;
          
        return ret;
    }

    @Override
    public List<Customer> retrieveCustomerList() throws Exception {
    	List<Customer> customerList = customerRepository.selectCustomerList();
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
