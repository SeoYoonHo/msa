package com.minibank.inquiry.controller;


import com.minibank.inquiry.domain.entity.Customer;
import com.minibank.inquiry.service.InquiryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class InquiryController {

    @Resource(name = "inquiryService")
    private InquiryService inquiryService;

    @Operation(summary = "고객상세조회", description = "고객상세조회")
    @RequestMapping(method = RequestMethod.GET, path = "/detail/rest/v0.8/{cstmId}")
    public Customer retrieveCustomerDetail(@PathVariable(name = "cstmId") String cstmId) throws Exception {
        return inquiryService.retrieveCustomerDetail(cstmId);
    }

    /*TODO (추가과제) 고객목록조회 */
    @Operation(summary = "고객목록조회", description = "고객목록조회")
    @GetMapping("/list/rest/v0.8")
    public List<Customer> retrieveCustomerList(@RequestParam String cstmId, @RequestParam String cstmNm,
                                               @RequestParam String acntNo) throws Exception {

        return inquiryService.selectCustomerList(cstmId, cstmNm, acntNo);
    }

}
