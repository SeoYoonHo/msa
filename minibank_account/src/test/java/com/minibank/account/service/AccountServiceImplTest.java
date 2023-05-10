package com.minibank.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.minibank.account.domain.entity.Account;
import com.minibank.account.domain.repository.AccountRepository;
import com.minibank.account.exception.BusinessException;
<<<<<<< HEAD
import com.minibank.account.rest.customer.CustomerFeignClient;
=======
import com.minibank.account.rest.customer.CustomerRestClient;
>>>>>>> lab#2-4
import com.minibank.account.rest.customer.entity.Customer;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock private AccountRepository accountRepository;
<<<<<<< HEAD
    @Mock private CustomerFeignClient customerFeignClient;
=======
    @Mock private CustomerRestClient customerRestClient;
>>>>>>> lab#2-4

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createAccount_WithNotExistAccount_WillCreateAccount() throws Exception {
        //given
        Account account = Account.builder()
                .acntNo("111101")
                .cstmId("1111")
                .cstmNm("홍길동")
                .acntNm("샘플계좌")
                .newDtm("2020-01-29 18:10:44").build();

        Customer customer = Customer.builder()
                .cstmId(account.getCstmId())
                .cstmNm(account.getCstmNm()).build();

        given(accountRepository.selectAccount(any(Account.class))).willReturn(null);
<<<<<<< HEAD
        given(customerFeignClient.retrieveCustomer(anyString())).willReturn(customer);
=======
        given(customerRestClient.retrieveCustomer(anyString())).willReturn(customer);
>>>>>>> lab#2-4
        given(accountRepository.insertAccount(account)).willReturn(1);

        //when
        accountService.createAccount(account);

        //then
<<<<<<< HEAD
        verify(customerFeignClient).retrieveCustomer(account.getCstmId());
=======
        verify(customerRestClient).retrieveCustomer(account.getCstmId());
>>>>>>> lab#2-4
        verify(accountRepository).insertAccount(account);
    }

    @Test
    void createAccount_WithExistAccount_ThrowBusinessException() throws Exception {

        //given
        Account account = Account.builder()
                .acntNo("111101")
                .cstmId("1111")
                .cstmNm("홍길동")
                .acntNm("샘플계좌")
                .newDtm("2020-01-29 18:10:44").build();

        given(accountRepository.selectAccount(any(Account.class))).willReturn(account);

        //when
        Throwable exception = assertThrows(BusinessException.class, () -> accountService.createAccount(account));

        //then
        assertThat(exception.getMessage()).isEqualTo("존재하는 계좌번호입니다.");
    }

}