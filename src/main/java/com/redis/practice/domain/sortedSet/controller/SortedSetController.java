package com.redis.practice.domain.sortedSet.controller;

import com.redis.practice.domain.sortedSet.model.SortedSet;
import com.redis.practice.domain.sortedSet.request.SortedSetRequest;
import com.redis.practice.service.RedisSortedSet;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "string", description = "sorted set api")
@RestController
@RequestMapping("/api/v1/sorted-set")
@RequiredArgsConstructor
public class SortedSetController {
    private final RedisSortedSet redis;

    @PostMapping("/sorted-set-collection")
    public void SetSortedSet(
            @RequestBody @Valid SortedSetRequest req
    ) {
        redis.setSortedSet(req);
    }

    @GetMapping("/get-sorted-set-by-range")
    public List<SortedSet> GetSortedSet(
            @RequestParam @Valid String key,
            @RequestParam @Valid Float min,
            @RequestParam @Valid Float max
    ) {
        return redis.getSetDataByRange(key, min, max);
    }

    @GetMapping("/get-sorted-set-by-top")
    public List<SortedSet> GetTopN(
            @RequestParam @Valid String key,
            @RequestParam @Valid Integer n
    ) {
        return redis.getTopN(key, n);
    }
}
