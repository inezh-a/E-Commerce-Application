package com.app.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {

    private Long couponId;

    private String code;

    private Double discountAmount;
    
    private Boolean active;
    
}