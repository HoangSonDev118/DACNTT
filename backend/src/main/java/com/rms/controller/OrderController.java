package com.rms.controller;

import com.rms.dto.request.OrderRequest;
import com.rms.dto.response.DailySummaryResponse;
import com.rms.dto.response.OrderResponse;
import com.rms.service.OrderService;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse create(@RequestBody OrderRequest req) {
        return orderService.create(req);
    }

    // API PUBLIC — không yêu cầu token
    @PostMapping("/public")
    public OrderResponse createPublic(@RequestBody OrderRequest req) {
        return orderService.create(req);
    }

    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable String id) {
        return orderService.getById(id);
    }

    @GetMapping
    public List<OrderResponse> getAll(@RequestParam(required = false) String tableId) {
        if (tableId != null) return orderService.getByTableId(tableId);
        return orderService.getAll();
    }

    @PutMapping("/{id}/status")
    public OrderResponse updateStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        return orderService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        orderService.delete(id);
    }

    @GetMapping("/summary/daily")
    public DailySummaryResponse getDailySummary(
            @RequestParam(required = false) String date) {
        LocalDate targetDate = date != null ? LocalDate.parse(date) : LocalDate.now();
        return orderService.getDailySummary(targetDate);
    }
}
