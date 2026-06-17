package com.market.controller;

import com.market.common.result.Result;
import com.market.entity.Transaction;
import com.market.entity.Refund;
import com.market.service.TransactionService;
import com.market.service.RefundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "财务管理")
@RestController
@RequestMapping("/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final TransactionService transactionService;
    private final RefundService refundService;

    @ApiOperation("交易流水列表")
    @GetMapping("/transactions")
    public Result<?> transactionList(@RequestParam(required = false) String transactionNo,
                                      @RequestParam(required = false) Integer transactionType,
                                      @RequestParam(required = false) Integer status,
                                      @RequestParam(required = false) String startTime,
                                      @RequestParam(required = false) String endTime) {
        return Result.success(transactionService.selectList(transactionNo, transactionType, status, startTime, endTime));
    }

    @ApiOperation("交易流水详情")
    @GetMapping("/transactions/{id}")
    public Result<?> transactionDetail(@PathVariable Long id) {
        return Result.success(transactionService.getById(id));
    }

    @ApiOperation("退款列表")
    @GetMapping("/refunds")
    public Result<?> refundList(@RequestParam(required = false) String refundNo,
                                 @RequestParam(required = false) Integer status,
                                 @RequestParam(required = false) String startTime,
                                 @RequestParam(required = false) String endTime) {
        return Result.success(refundService.selectList(refundNo, status, startTime, endTime));
    }

    @ApiOperation("退款详情")
    @GetMapping("/refunds/{id}")
    public Result<?> refundDetail(@PathVariable Long id) {
        return Result.success(refundService.getById(id));
    }

    @ApiOperation("审核退款（通过）")
    @PutMapping("/refunds/{id}/approve")
    public Result<?> approveRefund(@PathVariable Long id, @RequestParam(required = false) String note, @RequestParam Long handlerId) {
        return Result.success(refundService.handleRefund(id, 1, note, handlerId));
    }

    @ApiOperation("驳回退款")
    @PutMapping("/refunds/{id}/reject")
    public Result<?> rejectRefund(@PathVariable Long id, @RequestParam(required = false) String note, @RequestParam Long handlerId) {
        return Result.success(refundService.handleRefund(id, 2, note, handlerId));
    }

    @ApiOperation("待处理退款数量")
    @GetMapping("/refunds/pending/count")
    public Result<?> pendingRefundCount() {
        return Result.success(refundService.countByStatus(0));
    }
}