package com.kt.millet.helm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import io.lettuce.core.ReadFrom;

@Configuration
@EnableRedisRepositories
public class RedisRepositoryConfig {


	@Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceClientConfiguration configuration = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.SLAVE)
                .build();

        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort),
                configuration);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
    
}
