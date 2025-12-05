package com.rms.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private String id;
    private String tableId;
    private List<OrderItemResponse> items;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private String status;
}
