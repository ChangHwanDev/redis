package com.redis.practice.common.redis;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisCommon {
    private final RedisTemplate<String, String> template;
    private final Gson gson;

    @Value("${spring.data.redis.default-time}")
    private Duration defaultExpireTime;

    public <T> T getData(String key, Class<T> clazz) {
        String jsonValue = template.opsForValue().get(key);
        if (jsonValue == null) {
            return null;
        }

        return fromJson(jsonValue, clazz);
    }

    public <T> void setData(String key, T value) {
        String jsonValue = toJson(value);
        template.opsForValue().set(key, jsonValue, defaultExpireTime);
    }

    public <T> void multiSetData(Map<String, T> datas) {
        Map<String, String > jsonMap = new HashMap<>();

        for (Map.Entry<String, T> entry : datas.entrySet()) {
            jsonMap.put(entry.getKey(), toJson(entry.getValue()));
        }
        template.opsForValue().multiSet(jsonMap);
    }

    public <T> void addToSortedSet(String key, T value, Float score) {
        String jsonValue = toJson(value);
        template.opsForZSet().add(key, jsonValue, score);
    }


    public <T> List<T> rangeByScore(String key, Float minScore, Float maxScore, Class<T> clazz){
        Set<String> jsonValues = template.opsForZSet().rangeByScore(key, minScore, maxScore);
        List<T> resultSet = new ArrayList<>();

        if (jsonValues != null) {
            for (String jsonValue : jsonValues) {
                resultSet.add(fromJson(jsonValue, clazz));
            }
        }
        return resultSet;
    }

    public <T> List<T> getTopNFromSortedSet(String key, int n, Class<T> clazz){
        Set<String> jsonValues = template.opsForZSet().reverseRange(key, 0, n - 1);
        List<T> resultSet = new ArrayList<>();

        if (jsonValues != null) {
            for (String jsonValue : jsonValues) {
                resultSet.add(fromJson(jsonValue, clazz));
            }
        }
        return resultSet;
    }
    public <T> void addToListLeft(String key, T value) {
        String jsonValue = toJson(value);
        template.opsForList().leftPush(key, jsonValue);
    }

    public <T> void addToListRight(String key, T value) {
        String jsonValue = toJson(value);
        template.opsForList().rightPush(key, jsonValue);
    }

    public <T> List<T> getAllList(String key, Class<T> clazz) {
        List<String> jsonValues = template.opsForList().range(key, 0, -1);
        List<T> resultSet = new ArrayList<>();

        if (jsonValues != null) {
            for (String jsonValue : jsonValues) {
                resultSet.add(fromJson(jsonValue, clazz));
            }
        }
        return resultSet;
    }

    public <T> void removeFromList(String key, T value) {
        String jsonValue = toJson(value);
        template.opsForList().remove(key, 1, jsonValue);
    }

    public <T> void putInHash(String key, String field, T value) {
        String jsonValue = toJson(value);
        template.opsForHash().put(key, field, jsonValue);
    }

    public <T> T getFromHash(String key, String field, Class<T> clazz) {
        Object result = template.opsForHash().get(key, field);

        if (result != null) {
            return fromJson(result.toString(), clazz);
        }

        return null;
    }

    public void removeFromHash(String key, String field) {
        template.opsForHash().delete(key, field);
    }

    private <T> String toJson(T value) {
        return gson.toJson(value);
    }

    private <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public Boolean lock(Long key) {
        return template
                .opsForValue()
                .setIfAbsent(generateKey(key), "lock", Duration.ofMillis(3_000));
    }

    public void unlock(Long key) {
        template.delete(generateKey(key));
    }

    private String generateKey(Long key) {
        return key.toString();
    }
}
