package com.minibank.b2bt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.minibank.b2bt.domain.TransferHistory;
import com.minibank.b2bt.publisher.B2BDepositProducer;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "/b2bt")
public class DepositController {
	
    @Autowired
    B2BDepositProducer b2BDepositProducer;

     /*타행 이체*/
    @Operation(summary = "타행입금테스트", description = "타행입금테스트")
    @RequestMapping(method = RequestMethod.POST, path = "/deposit")
    public Boolean btobDepositTest(@RequestBody TransferHistory input) throws Exception{
    	
        // 타행입금처리 결과 publish
    	b2BDepositProducer.sendB2BDepositMessage(input); // sendB2BDepositMessage(TransferHistory transferResult) 
    	 
    	return true;
    }
}
