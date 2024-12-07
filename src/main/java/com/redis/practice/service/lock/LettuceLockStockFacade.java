package com.redis.practice.service.lock;

import com.redis.practice.common.redis.RedisCommon;
import com.redis.practice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {

    private final RedisCommon redis;

    private final StockService stockService;

    public void decrease(Long key, Long quantity) throws InterruptedException {
        while (!redis.lock(key)) {
            Thread.sleep(100);
        }

        try {
            System.out.println("decrease");
            stockService.decrease(key, quantity);
        } finally {
            redis.unlock(key);
        }
    }
}