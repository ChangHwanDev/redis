package com.redis.practice.domain.string.controller;

import com.redis.practice.domain.string.model.request.MultiStringRequest;
import com.redis.practice.domain.string.model.request.StringRequest;
import com.redis.practice.domain.string.model.response.StringResponse;
import com.redis.practice.service.RedisString;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "string", description = "string api")
@RestController
@RequestMapping("/api/v1/set")
@RequiredArgsConstructor
public class StringController {

    private final RedisString redis;

    @PostMapping("/set-string-colletion")
    public void setString(
            @RequestBody @Valid StringRequest req
            ) {
        redis.set(req);
    }

    @GetMapping("/get-string-collection")
    public StringResponse getString(
            @RequestParam @Valid String key
    ) {
        return redis.get(key);
    }

    @PostMapping("/multi-set-collection")
    public void multiString(
            @RequestBody @Valid MultiStringRequest req
    ) {
        redis.multiSet(req);
    }
}
