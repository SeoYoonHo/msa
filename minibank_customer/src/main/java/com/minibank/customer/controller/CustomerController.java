package com.minibank.customer.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minibank.customer.domain.entity.Customer;
import com.minibank.customer.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;


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

    @Operation(summary = "고객존재여부조회", description = "고객존재여부조회")
    @RequestMapping(method = RequestMethod.GET, path = "/exists/rest/v0.8/{cstmId}")
    public Boolean existsCustomerId(@PathVariable(name = "cstmId") String cstmId) throws Exception{
    	return customerService.existsCustomerId(cstmId);
    }
    
    @Operation(summary = "고객목록조회", description = "고객목록조회")
    @Parameters({
    	@Parameter(name= "cstmId", description = "고객ID", required=false, in = ParameterIn.QUERY),
    	@Parameter(name="cstmNm", description ="고객이름", required=false, in = ParameterIn.QUERY),
    	@Parameter(name="acntNo", description ="계좌번호", required=false, in = ParameterIn.QUERY)
    })
    @RequestMapping(method = RequestMethod.GET, path = "/list/rest/v0.8")
    public List<Customer> retrieveCustomerList(@RequestParam(defaultValue = "") String cstmId, 
    											@RequestParam(defaultValue = "") String cstmNm,
    											@RequestParam(defaultValue = "") String acntNo ) throws Exception{
        return customerService.retrieveCustomerList(cstmId , cstmNm, acntNo);
    }
    
    @Operation(summary = "고객수정", description = "고객수정")
    @RequestMapping(method = RequestMethod.PUT, path = "/rest/v0.8/{cstmId}")
    public Integer updateCustomer(@PathVariable(name = "cstmId") String cstmId, 
    							   @RequestBody Customer customer) throws Exception{
         return customerService.updateCustomer(customer);
    }

}