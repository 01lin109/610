package com.market.dto;

import lombok.Data;
import java.util.Map;

@Data
public class DashboardDTO {
    private Long todayOrderCount;
    private Long todayMemberCount;
    private String todaySalesAmount;
    private Long pendingOrderCount;
    private Long pendingRefundCount;
    private Long totalProductCount;
    private Long totalMemberCount;
    private Map<String, Object> salesTrend;
    private Map<String, Object> orderStatusStat;
    private Map<String, Object> topProducts;
}