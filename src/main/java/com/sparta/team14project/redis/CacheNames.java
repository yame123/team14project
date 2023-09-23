package com.sparta.team14project.redis;

import java.time.Duration;

public class CacheNames {
    public static final String SEARCH_CACHE = "searchCache";
    public static final String RANK_CACHE = "rankCache";
    public static final String ALL_STORE_CACHE = "allStoreCache";

    public static final Duration SEARCH_TTL = Duration.ofSeconds(20);
    public static final Duration RANK_TTL = Duration.ofHours(1);
    public static final Duration ALL_STORE_TTL = Duration.ofSeconds(30);
}