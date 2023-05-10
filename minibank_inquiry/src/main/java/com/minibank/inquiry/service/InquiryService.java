package com.minibank.inquiry.service;

import java.util.List;

import com.minibank.inquiry.domain.entity.Account;
import com.minibank.inquiry.domain.entity.Customer;

public interface InquiryService {
    public Customer retrieveCustomerDetail(String cstmId) throws Exception;
    public int createCustomer(Customer customer) throws Exception;
    public int createAccount(Account account) throws Exception;
    public int updateTransferLimit(Customer customer) throws Exception;
    public int updateAccountBalance(Account account) throws Exception;
    
    /*TODO (추가과제) 전체 계좌목록조회 */

}
