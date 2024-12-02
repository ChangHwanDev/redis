package com.redis.practice.domain.hashes.controller;

import com.redis.practice.domain.hashes.model.HashModel;
import com.redis.practice.domain.hashes.model.request.HashRequest;
import com.redis.practice.service.RedisHash;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "hash", description = "hash api")
@RestController
@RequestMapping("/api/v1/hash")
@RequiredArgsConstructor
public class HashesController {

    private final RedisHash redis;

    @PostMapping("/put-hashes")
    public void PutHashes(
            @RequestBody @Valid HashRequest req
    ) {
        redis.putInHash(req.baseRequest().key(), req.field(), req.name());
    }

    @GetMapping("/get-hash-value")
    public HashModel GetHashes (
            @RequestParam @Valid String key,
            @RequestParam @Valid String field
    ) {
        return redis.getFromHash(key, field);
    }

}