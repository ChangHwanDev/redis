package com.redis.practice.service;

import com.redis.practice.common.redis.RedisCommon;
import com.redis.practice.domain.sortedSet.model.SortedSet;
import com.redis.practice.domain.sortedSet.request.SortedSetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisSortedSet {

    private final RedisCommon redis;

    public void setSortedSet(SortedSetRequest req) {
        SortedSet model = new SortedSet(req.name(), req.score());
        redis.addToSortedSet(req.baseRequest().key(), model, req.score());
    }

    public List<SortedSet> getSetDataByRange(String key, Float min, Float max) {
        return redis.rangeByScore(key, min, max, SortedSet.class);
    }

    public List<SortedSet> getTopN(String key, Integer n) {
        return redis.getTopNFromSortedSet(key, n, SortedSet.class);
    }
}
