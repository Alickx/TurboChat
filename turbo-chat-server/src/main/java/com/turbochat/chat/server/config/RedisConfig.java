package com.turbochat.chat.server.config;

import com.turbochat.chat.common.redis.RedisHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Description Redis 配置
 * @Date 2023/7/13 23:05
 * @Created by Alickx
 */
@Configuration
public class RedisConfig {

	@Bean
	public RedisHelper redisHelper(StringRedisTemplate template) {
		RedisHelper.setRedisTemplate(template);
		return RedisHelper.INSTANCE;
	}

}
