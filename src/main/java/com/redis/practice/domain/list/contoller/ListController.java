package com.redis.practice.domain.list.contoller;

import com.redis.practice.domain.list.model.ListModel;
import com.redis.practice.domain.list.model.request.ListRequest;
import com.redis.practice.service.RedisList;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "list", description = "list api")
@RestController
@RequestMapping("/api/v1/list")
@RequiredArgsConstructor
public class ListController {
    private final RedisList redis;

    @PostMapping("/list-add-left")
    public void SetNewValueToList(
            @RequestBody @Valid ListRequest req
    ) {
        redis.addToListLeft(req.baseRequest().key(), req.name());
    }

    @PostMapping("/list-add-right")
    public void SetNewValueToRight(
            @RequestBody @Valid ListRequest req
    ) {
        redis.addToListRight(req.baseRequest().key(), req.name());
    }

    @GetMapping("/all")
    public List<ListModel> GetAll(
            @RequestParam String key
    ) {
        return redis.getAllData(key);
    }
}