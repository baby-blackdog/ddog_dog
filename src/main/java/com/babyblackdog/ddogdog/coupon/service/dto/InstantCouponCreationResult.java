package com.babyblackdog.ddogdog.coupon.service.dto;

import java.time.LocalDate;

public record InstantCouponCreationResult(Long couponId, Long roomId, String couponName, String couponType,
                                          String discountType, Double discountValue, LocalDate startDate,
                                          LocalDate endDate) {

}