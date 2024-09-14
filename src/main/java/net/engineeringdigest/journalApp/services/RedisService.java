package net.engineeringdigest.journalApp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api_Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass) {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            if (o != null) {
                return mapper.readValue(o.toString(), entityClass);
            }
        } catch (Exception e) {
            log.error("Exception", e);
        }
        return null;
    }

    public void set(String key, Object o, Long ttl) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, json, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception", e);
        }
    }
}
