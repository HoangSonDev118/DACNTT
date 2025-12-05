package com.rms.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String tableId;
    private List<OrderItemRequest> items;
}
