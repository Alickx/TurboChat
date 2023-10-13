package com.turbochat.chat.server.security.resourceserver.configuration;

import com.turbochat.chat.authentication.AnonymousForeverAuthenticationProvider;
import com.turbochat.chat.oatuh2.server.resource.properties.OAuth2ResourceServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * @Description 开启匿名支持
 * @Date 2023/7/27 11:52
 * @Created by Alickx
 */
@Component
@RequiredArgsConstructor
public class MyGlobalAuthenticationConfigurerAdapter extends GlobalAuthenticationConfigurerAdapter {

	private final OAuth2ResourceServerProperties resourceServerProperties;

	@Override
	public void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(
			new AnonymousForeverAuthenticationProvider(resourceServerProperties.getIgnoreUrls()));
	}

}
