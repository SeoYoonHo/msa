package com.minibank.customer.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.minibank.customer.domain.entity.Customer;
import com.minibank.customer.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class CustomerController {

    @Resource(name = "customerService")
    private CustomerService customerService;
    
    @Operation(summary = "고객등록", description = "고객등록")
    @RequestMapping(method = RequestMethod.POST, path = "/rest/v0.8")
    public Integer createCustomer(@RequestBody Customer customer) throws Exception{
         return customerService.createCustomer(customer);
    }

    @Operation(summary = "고객기본조회", description = "고객등록")
    @RequestMapping(method = RequestMethod.GET, path = "/rest/v0.8/{cstmId}")
    public Customer retrieveCustomer(@PathVariable(name = "cstmId") String cstmId) throws Exception{

    	//TODO : 10초 응답지연 코드 추가
    	Thread.sleep(10000);
    	
        return customerService.retrieveCustomer(cstmId);
    }

    @Operation(summary = "고객존재여부조회", description = "고객존재여부조회")
    @RequestMapping(method = RequestMethod.GET, path ="/exists/rest/v0.8/{cstmId}")
    public Boolean existsCustomerId(@PathVariable(name = "cstmId") String cstmId) throws Exception{
    	return customerService.existsCustomerId(cstmId);
    }
    
    @Operation(summary = "고객목록조회", description = "고객목록조회")
    @GetMapping("/rest/v0.8")
    public List<Customer> retrieveCustomerList() throws Exception{
        return customerService.retrieveCustomerList();
    }
    
    @Operation(summary = "고객수정", description = "고객수정")
    @PutMapping("/rest/v0.8/{cstmId}")
    public Integer updateCustomer(@PathVariable(name = "cstmId") String cstmId, 
    							   @RequestBody Customer customer) throws Exception{
         return customerService.updateCustomer(customer);
    }

}