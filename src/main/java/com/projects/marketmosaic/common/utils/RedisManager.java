package com.projects.marketmosaic.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisManager {
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Set a key-value pair in Redis with optional expiration
     *
     * @param key      The key to set
     * @param value    The value to set
     * @param timeout  The timeout duration
     * @param timeUnit The time unit for the timeout
     */
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value);
        if (timeout > 0) {
            redisTemplate.expire(key, timeout, timeUnit);
        }
    }

    /**
     * Set a key-value pair in Redis without expiration
     *
     * @param key   The key to set
     * @param value The value to set
     */
    public void set(String key, Object value) {
        set(key, value, 0, TimeUnit.SECONDS);
    }

    /**
     * Get a value from Redis by key
     *
     * @param key The key to get
     * @return The value associated with the key, or null if not found
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Delete a key from Redis
     *
     * @param key The key to delete
     * @return true if the key was deleted, false if the key didn't exist
     */
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * Increment a counter in Redis
     *
     * @param key The key to increment
     * @return The new value after incrementing
     */
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * Check if a key exists in Redis
     *
     * @param key The key to check
     * @return true if the key exists, false otherwise
     */
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * Set expiration time for a key
     *
     * @param key      The key to set expiration for
     * @param timeout  The timeout duration
     * @param timeUnit The time unit for the timeout
     * @return true if the expiration was set, false if the key doesn't exist
     */
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, timeUnit));
    }

    /**
     * Get the remaining time to live for a key
     *
     * @param key The key to check
     * @return The remaining time in seconds, or -1 if the key doesn't exist or has
     *         no expiration
     */
    public Long getTimeToLive(String key) {
        return redisTemplate.getExpire(key);
    }
}