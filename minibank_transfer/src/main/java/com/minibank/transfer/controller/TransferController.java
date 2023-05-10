package com.minibank.transfer.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.minibank.transfer.domain.entity.TransferHistory;
import com.minibank.transfer.domain.entity.TransferLimit;
import com.minibank.transfer.service.TransferService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class TransferController {

    @Resource(name = "transferService")
    private TransferService transferService;

    @Operation(summary = "이체이력조회", description = "이체이력조회")
    @RequestMapping(method = RequestMethod.GET, path = "/transfer-history/rest/v0.8/{cstmId}")
    public List<TransferHistory> retrieveTransferHistoryList(@PathVariable(name = "cstmId") String cstmId) throws Exception{
        List<TransferHistory> transferHistory = transferService.retrieveTransferHistoryList(cstmId);
        return transferHistory;
    }

    @Operation(summary = "이체한도조회", description = "이체한도조회")
    @RequestMapping(method = RequestMethod.GET, path = "/transfer-limit/rest/v0.8/{cstmId}")
    public TransferLimit retrieveTransferLimit(@PathVariable(name = "cstmId") String cstmId) throws Exception{
        return transferService.retrieveTransferLimit(cstmId);
    }

    @Operation(summary = "이체가능한도조회", description = "이체가능한도조회")
    @RequestMapping(method = RequestMethod.GET, path = "/transfer-limit/enable/rest/v0.8/{cstmId}")
    public TransferLimit retrieveEnableTransferLimit(@PathVariable(name = "cstmId") String cstmId) throws Exception{
        return  transferService.retrieveEnableTransferLimit(cstmId);
    }
}
