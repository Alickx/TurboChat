package com.turbochat.chat.oatuh2.server.resource.configurer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @Description 对默认配置的 OAuth2ResourceServerConfigurer 进行自定义处理
 * @Date 2023/7/19 14:07
 * @Created by Alickx
 */
public interface OAuth2ResourceServerConfigurerCustomizer {

	/**
	 * 对资源服务器配置进行自定义
	 * @param httpSecurity security configuration
	 */
	void customize(HttpSecurity httpSecurity) throws Exception;

}
