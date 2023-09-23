package com.sparta.team14project.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        RedisSerializationContext.SerializationPair<Object> jsonSerializer =
                RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer);

        RedisSerializationContext.SerializationPair<String> keySerializer =
                RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(
                        RedisCacheConfiguration.defaultCacheConfig()
                                .serializeKeysWith(keySerializer)
                                .serializeValuesWith(jsonSerializer)
                                .entryTtl(Duration.ofSeconds(20))
                )
                .build();

        return redisCacheManager;
    }
}
