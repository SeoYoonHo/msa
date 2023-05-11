package com.minibank.inquiry.service;

import com.minibank.inquiry.domain.entity.Account;
import com.minibank.inquiry.domain.entity.Customer;

import java.util.List;

public interface InquiryService {
    public Customer retrieveCustomerDetail(String cstmId) throws Exception;

    public int createCustomer(Customer customer) throws Exception;

    public int createAccount(Account account) throws Exception;

    public int updateTransferLimit(Customer customer) throws Exception;

    public int updateAccountBalance(Account account) throws Exception;

    /*TODO (추가과제) 전체 계좌목록조회 */
    public List<Customer> selectCustomerList(String cstmId, String cstmNm,
                                               String acntNo) throws Exception;

}
