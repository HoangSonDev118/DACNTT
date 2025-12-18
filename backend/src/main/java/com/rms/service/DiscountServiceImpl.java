package com.rms.service;

import com.rms.dto.request.DiscountRequest;
import com.rms.dto.response.DiscountResponse;
import com.rms.exception.ResourceNotFoundException;
import com.rms.model.Discount;
import com.rms.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    @Override
    public DiscountResponse create(DiscountRequest request) {
        Discount discount = Discount.builder()
                .code(request.getCode())
                .discountPercent(request.getDiscountPercent())
                .minOrder(request.getMinOrder())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(request.getStatus())
                .build();
        discountRepository.save(discount);
        return toResponse(discount);
    }

    @Override
    public DiscountResponse update(String id, DiscountRequest request) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found"));
        discount.setCode(request.getCode());
        discount.setDiscountPercent(request.getDiscountPercent());
        discount.setMinOrder(request.getMinOrder());
        discount.setStartDate(request.getStartDate());
        discount.setEndDate(request.getEndDate());
        discount.setStatus(request.getStatus());
        discountRepository.save(discount);
        return toResponse(discount);
    }

    @Override
    public void delete(String id) {
        if (!discountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Discount not found");
        }
        discountRepository.deleteById(id);
    }

    @Override
    public DiscountResponse getById(String id) {
        return discountRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found"));
    }

    @Override
    public DiscountResponse getByCode(String code) {
        return discountRepository.findByCode(code)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found with code: " + code));
    }

    @Override
    public List<DiscountResponse> getAll() {
        return discountRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private DiscountResponse toResponse(Discount discount) {
        DiscountResponse response = new DiscountResponse();
        response.setId(discount.getId());
        response.setCode(discount.getCode());
        response.setDiscountPercent(discount.getDiscountPercent());
        response.setMinOrder(discount.getMinOrder());
        response.setStartDate(discount.getStartDate());
        response.setEndDate(discount.getEndDate());
        response.setStatus(discount.getStatus());
        return response;
    }
}
