package com.redis.practice.service;


import com.redis.practice.common.redis.RedisCommon;
import com.redis.practice.domain.list.model.ListModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisList {
    final private RedisCommon redis;

    public void addToListLeft(String key, String name) {
        ListModel model = new ListModel(name);
        redis.addToListLeft(key, model);
    }

    public void addToListRight(String key, String name) {
        ListModel model = new ListModel(name);
        redis.addToListRight(key, model);
    }

    public List<ListModel> getAllData(String key) {
        return redis.getAllList(key, ListModel.class);
    }
}
