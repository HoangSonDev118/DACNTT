package com.rms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailySummaryResponse {
    private LocalDate date;
    private Long totalOrders;
    private Long completedOrders;
    private Long cancelledOrders;
    private Long pendingOrders;
    private Double totalRevenue;
    private Double averageOrderValue;
}
