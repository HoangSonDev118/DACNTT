package com.rms.service;

import com.rms.dto.request.PaymentRequest;
import com.rms.dto.response.PaymentResponse;
import com.rms.exception.BadRequestException;
import com.rms.exception.ResourceNotFoundException;
import com.rms.model.Payment;
import com.rms.repository.OrderRepository;
import com.rms.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    public PaymentResponse create(PaymentRequest request) {
        // Validate order exists
        if (!orderRepository.existsById(request.getOrderId())) {
            throw new BadRequestException("Order not found");
        }

        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .paidAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);
        return toResponse(payment);
    }

    @Override
    public PaymentResponse getById(String id) {
        return paymentRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    @Override
    public List<PaymentResponse> getByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<PaymentResponse> getAll() {
        return paymentRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(String id) {
        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payment not found");
        }
        paymentRepository.deleteById(id);
    }

    private PaymentResponse toResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setOrderId(payment.getOrderId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPaidAt(payment.getPaidAt());
        return response;
    }
}
