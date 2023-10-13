package com.turbochat.chat.oauth2.server.authorization.config.configurer;

import cn.hutool.core.lang.Assert;
import com.turbochat.chat.oauth2.server.authorization.web.filter.LoginPasswordDecoderFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.web.OAuth2ClientAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @Description
 * @Date 2023/7/19 13:21
 * @Created by Alickx
 */
@Order(100)
public class OAuth2LoginPasswordDecoderConfigurer
extends OAuth2AuthorizationServerExtensionConfigurer<OAuth2LoginPasswordDecoderConfigurer, HttpSecurity>{

	private final String passwordSecretKey;

	public OAuth2LoginPasswordDecoderConfigurer(String passwordSecretKey) {
		Assert.notEmpty(passwordSecretKey, "passwordSecretKey can not be null");
		this.passwordSecretKey = passwordSecretKey;
	}

	@Override
	public void configure(HttpSecurity httpSecurity) {
		// 获取授权服务器配置
		AuthorizationServerSettings authorizationServerSettings = httpSecurity
			.getSharedObject(AuthorizationServerSettings.class);

		// 只处理登录接口
		AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(authorizationServerSettings.getTokenEndpoint(),
			HttpMethod.POST.name());

		// 密码解密，必须在 OAuth2ClientAuthenticationFilter 过滤器之后，方便获取当前客户端
		httpSecurity.addFilterAfter(new LoginPasswordDecoderFilter(requestMatcher, passwordSecretKey),
			OAuth2ClientAuthenticationFilter.class);
	}

}
