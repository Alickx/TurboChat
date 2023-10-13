package com.turbochat.chat.oauth2.server.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description 用于序列化 OAuth2Authorization 的专用 ObjectMapper 定制器
 * @Date 2023/7/19 12:16
 * @Created by Alickx
 */
@FunctionalInterface
public interface OAuth2AuthorizationObjectMapperCustomizer {

	void customize(ObjectMapper objectMapper);

}
