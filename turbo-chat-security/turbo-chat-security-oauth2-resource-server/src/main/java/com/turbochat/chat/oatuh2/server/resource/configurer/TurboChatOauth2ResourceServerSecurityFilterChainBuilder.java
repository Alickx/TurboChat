package com.turbochat.chat.oatuh2.server.resource.configurer;

import cn.hutool.core.util.ArrayUtil;
import com.turbochat.chat.oatuh2.server.resource.properties.OAuth2ResourceServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Date 2023/7/19 14:06
 * @Created by Alickx
 */
@RequiredArgsConstructor
public class TurboChatOauth2ResourceServerSecurityFilterChainBuilder
	implements Oauth2ResourceServerSecurityFilterChainBuilder {

	private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

	private final AuthenticationEntryPoint authenticationEntryPoint;

	private final BearerTokenResolver bearerTokenResolver;

	private final ObjectProvider<List<OAuth2ResourceServerConfigurerCustomizer>> configurerCustomizersProvider;

	private final ObjectProvider<List<OAuth2ResourceServerExtensionConfigurer<HttpSecurity>>> extensionConfigurersProvider;

	@Override
	public SecurityFilterChain build(HttpSecurity http) throws Exception {

		String[] paths = ArrayUtil.toArray(oAuth2ResourceServerProperties.getIgnoreUrls(), String.class);
		String pathString = String.join(",", paths);
		http
			.authorizeHttpRequests((authorizeHttpRequests) ->
				authorizeHttpRequests.requestMatchers(new AntPathRequestMatcher(pathString))
					.permitAll()
					.anyRequest().authenticated()
			).csrf(AbstractHttpConfigurer::disable)
			.oauth2ResourceServer((oauth2ResourceServer) ->
				oauth2ResourceServer.authenticationEntryPoint(authenticationEntryPoint)
					.bearerTokenResolver(bearerTokenResolver)
					.opaqueToken(Customizer.withDefaults())
			);


		// 允许嵌入iframe
		if (!oAuth2ResourceServerProperties.isIframeDeny()) {
			http.headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
		}

		// 自定义处理
		List<OAuth2ResourceServerConfigurerCustomizer> configurerCustomizers = configurerCustomizersProvider
			.getIfAvailable(Collections::emptyList);
		for (OAuth2ResourceServerConfigurerCustomizer configurerCustomizer : configurerCustomizers) {
			configurerCustomizer.customize(http);
		}

		// 扩展配置
		List<OAuth2ResourceServerExtensionConfigurer<HttpSecurity>> extensionConfigurers = extensionConfigurersProvider
			.getIfAvailable(Collections::emptyList);
		for (OAuth2ResourceServerExtensionConfigurer<HttpSecurity> extensionConfigurer : extensionConfigurers) {
			http.apply(extensionConfigurer);
		}

		return http.build();
	}

}
