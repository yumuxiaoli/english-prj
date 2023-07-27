package com.enstudy.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置
 *
 * @author haowei
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        // 设置序列化配置，不然会出现乱码问题
        RedisTemplate template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        //字符串序列化方法
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
