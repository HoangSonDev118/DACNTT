package com.rms.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("discounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    @Id
    private String id;

    private String code;
    private Double discountPercent;
    private Double minOrder;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
