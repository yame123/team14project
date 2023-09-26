package com.sparta.team14project.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        RedisSerializationContext.SerializationPair<Object> jsonSerializer =
                RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer);

        RedisSerializationContext.SerializationPair<String> keySerializer =
                RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        cacheConfigurations.put(CacheNames.SEARCH_CACHE, RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(keySerializer)
                .serializeValuesWith(jsonSerializer)
                .entryTtl(CacheNames.SEARCH_TTL));

        cacheConfigurations.put(CacheNames.RANK_CACHE, RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(keySerializer)
                .serializeValuesWith(jsonSerializer)
                .entryTtl(CacheNames.RANK_TTL));

        cacheConfigurations.put(CacheNames.ALL_STORE_CACHE, RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(keySerializer)
                .serializeValuesWith(jsonSerializer)
                .entryTtl(CacheNames.ALL_STORE_TTL));

        cacheConfigurations.put(CacheNames.STORE_INFO_CACHE, RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(keySerializer)
                .serializeValuesWith(jsonSerializer) // JSON 직렬화를 사용
                .entryTtl(CacheNames.STORE_INFO_TTL));

        cacheConfigurations.put(CacheNames.REVIEW_CACHE, RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(keySerializer)
                .serializeValuesWith(jsonSerializer) // JSON 직렬화를 사용
                .entryTtl(CacheNames.REVIEW_CACHE_TTL));

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();

        return redisCacheManager;
    }
}
