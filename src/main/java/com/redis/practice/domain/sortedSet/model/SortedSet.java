package com.redis.practice.domain.sortedSet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SortedSet {
    String Name;
    Float score;
}
