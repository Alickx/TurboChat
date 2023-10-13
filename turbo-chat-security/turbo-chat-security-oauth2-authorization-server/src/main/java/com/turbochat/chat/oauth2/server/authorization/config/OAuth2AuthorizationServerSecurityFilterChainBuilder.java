package com.turbochat.chat.oauth2.server.authorization.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Description OAuth2 Authorization Server 的 SecurityFilterChain 构造器
 * @Date 2023/7/19 12:19
 * @Created by Alickx
 */
public interface OAuth2AuthorizationServerSecurityFilterChainBuilder {

	/**
	 * 构建 OAuth2 Authorization Server 的 SecurityFilterChain
	 * @param http HttpSecurity
	 * @return SecurityFilterChain
	 * @throws Exception 构建异常
	 */
	SecurityFilterChain build(HttpSecurity http) throws Exception;

}
