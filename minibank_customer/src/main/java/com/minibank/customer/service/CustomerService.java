package com.minibank.customer.service;

import java.util.List;

import com.minibank.customer.domain.entity.Customer;

public interface CustomerService {
    public int createCustomer(Customer customer) throws Exception;
    public Customer retrieveCustomer(String cstmId) throws Exception;
    public boolean existsCustomerId(String cstmId) throws Exception;
}
