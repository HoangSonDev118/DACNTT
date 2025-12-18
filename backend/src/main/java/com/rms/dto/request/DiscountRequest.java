package com.rms.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiscountRequest {
    private String code;
    private Double discountPercent;
    private Double minOrder;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
