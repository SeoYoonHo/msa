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

        if(customerRepository.existsCustomer(customer) > 0){
            throw new BusinessException("중복 아이디가 존재합니다");
        }

        customerRepository.insertCustomer(customer);
        
        return result;
    }

    @Override
    public Customer retrieveCustomer(String cstmId) throws Exception {
        Customer customer = new Customer();

        customer.setCstmId(cstmId);
        customer = customerRepository.selectCustomer(customer);
        
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

}
