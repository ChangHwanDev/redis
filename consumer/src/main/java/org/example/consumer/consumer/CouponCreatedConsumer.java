package org.example.consumer.consumer;

import org.example.consumer.domain.Coupon;
import org.example.consumer.domain.FailedEvent;
import org.example.consumer.repository.CouponRepository;
import org.example.consumer.repository.FailedEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CouponCreatedConsumer {

    private final CouponRepository couponRepository;
    private final FailedEventRepository failedEventRepository;

    public CouponCreatedConsumer(CouponRepository couponRepository, FailedEventRepository failedEventRepository) {
        this.couponRepository = couponRepository;
        this.failedEventRepository = failedEventRepository;
    }

    @KafkaListener(topics = "coupon_create", groupId = "group_1")
    public void listener(Long userId) {
       try{
           couponRepository.save(new Coupon(userId));
       } catch (Exception e){
           failedEventRepository.save(new FailedEvent(userId));
       }
    }
}
