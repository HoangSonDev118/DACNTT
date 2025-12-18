package com.rms.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiscountResponse {
    private String id;
    private String code;
    private Double discountPercent;
    private Double minOrder;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
