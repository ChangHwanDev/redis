package com.redis.practice.service;

import com.redis.practice.common.redis.RedisCommon;
import com.redis.practice.domain.hashes.model.HashModel;
import com.redis.practice.domain.list.model.ListModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisHash {
    final private RedisCommon redis;

    public void putInHash(String key, String field, String name) {
        HashModel model = new HashModel(name);
        redis.putInHash(key, field, model);
    }

    public HashModel getFromHash(String key, String field) {
        return redis.getFromHash(key, field, HashModel.class);
    }
}
