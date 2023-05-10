package com.minibank.inquiry.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minibank.inquiry.domain.entity.Customer;
import com.minibank.inquiry.service.InquiryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

@RestController
public class InquiryController {

    @Resource(name = "inquiryService")
    private InquiryService inquiryService;
    
    @Operation(summary = "고객상세조회", description = "고객상세조회")
    @RequestMapping(method = RequestMethod.GET, path = "/detail/rest/v0.8/{cstmId}")
    public Customer retrieveCustomerDetail(@PathVariable(name = "cstmId") String cstmId) throws Exception{
        return inquiryService.retrieveCustomerDetail(cstmId);
    }
    
    /*TODO (추가과제) 고객목록조회 */

}
