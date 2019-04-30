package com.dripop.core.util;

import com.alibaba.fastjson.TypeReference;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by liyou on 2017/9/18.
 */
public enum RedisUtil {

    INSTANCE;

    private StringRedisTemplate redisTemplate;

    RedisUtil() {
        redisTemplate = SpringContextUtil.getContext().getBean(StringRedisTemplate.class);
    }

    public void set(String key, String val, Integer expire) {
        if(expire == null) {
            redisTemplate.opsForValue().set(key, val);
        }else {
            redisTemplate.opsForValue().set(key, val, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object obj, Integer expire) {
        if(expire == null) {
            redisTemplate.opsForValue().set(key, JsonUtil.toAllJson(obj));
        }else {
            redisTemplate.opsForValue().set(key, JsonUtil.toAllJson(obj), expire, TimeUnit.SECONDS);
        }
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Object getObj(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long increment(String key, Long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    public <T> T get(String key, Class<T> clz) {
        String val = redisTemplate.opsForValue().get(key);
        if(StringUtil.isBlank(val)) {
            return null;
        }
        return JsonUtil.fromJson(val, clz);
    }

    public <T> T get(String key, TypeReference<T> type) {
        String val = redisTemplate.opsForValue().get(key);
        if(StringUtil.isBlank(val)) {
            return null;
        }
        return JsonUtil.fromJson(val, type);
    }

    public Boolean exists(final String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                return redisConnection.exists(keyByte);
            }
        });
    }

    public void expire(final String key, final Integer expire) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                return redisConnection.expire(keyByte, expire);
            }
        });
    }

    public void delete(final String... keys) {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[][] keyBytes = new byte[][]{{}};
                for (int i = 0; i < keys.length; i++) {
                    byte[] keyByte = redisTemplate.getStringSerializer().serialize(keys[i]);
                    keyBytes[i] = keyByte;
                }
                return redisConnection.del(keyBytes);
            }
        });
    }

    public Set<String> keys(String key) {
        return redisTemplate.keys(key);
    }

    public Boolean setnx(final String key, final String val, final Integer expire) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                byte[] valueByte = redisTemplate.getStringSerializer().serialize(val);
                Boolean result = redisConnection.setNX(keyByte, valueByte);
                if(result) {
                    redisConnection.expire(keyByte, expire);
                }
                return result;
            }
        });
    }

    public Long incr(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                Long result = redisConnection.incr(keyByte);
                return result;
            }
        });
    }

    public Long ttl(final String key) {
        return redisTemplate.getExpire(key);
    }
}
