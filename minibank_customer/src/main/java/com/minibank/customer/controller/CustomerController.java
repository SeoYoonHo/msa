package com.minibank.customer.controller;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
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
		return customerService.retrieveCustomer(cstmId);
	}

	@Operation(summary = "고객상세조회", description = "고객상세조회")
	@RequestMapping(method = RequestMethod.GET, path = "/detail/rest/v0.8/{cstmId}")
	public Customer retrieveCustomerDetail(@PathVariable(name = "cstmId") String cstmId) throws Exception{
		return customerService.retrieveCustomerDetail(cstmId);
	}

	@Operation(summary = "고객존재여부조회", description = "고객존재여부조회")
	@RequestMapping(method = RequestMethod.GET, path ="/exists/rest/v0.8/{cstmId}")
	public Boolean existsCustomerId(@PathVariable(name = "cstmId") String cstmId) throws Exception{
		return customerService.existsCustomerId(cstmId);
	}

}