package motgolla.global.util;

import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.List;

public final class RedisUtil {

    private static RedisTemplate<String, String> redisTemplate;

    public static void init(RedisTemplate<String, String> template) {
        redisTemplate = template;
    }

    public static void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public static void set(String key, String value, long timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(timeoutSeconds));
    }

    public static String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public static void deleteAll(List<String> keys) {
        redisTemplate.delete(keys);
    }
}
