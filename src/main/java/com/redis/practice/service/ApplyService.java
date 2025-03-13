package com.redis.practice.service;

import com.redis.practice.domain.Coupon.Coupon;
import com.redis.practice.repository.CouponCountRepository;
import com.redis.practice.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;

    public void apply(Long userId) {
        long count = couponCountRepository.increment();

        if (count > 100) {
            return;
        }

        couponRepository.save(new Coupon(userId));
    }
}
