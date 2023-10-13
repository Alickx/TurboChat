package com.turbochat.chat.oauth2.server.authorization.config.customizer;

import com.turbochat.chat.oauth2.server.authorization.authentication.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import com.turbochat.chat.oauth2.server.authorization.config.configurer.OAuth2ConfigurerUtils;
import com.turbochat.chat.oauth2.server.authorization.web.authentication.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * @Description 添加密码模式的支持
 * @Date 2023/7/19 13:23
 * @Created by Alickx
 */
@RequiredArgsConstructor
public class OAuth2ResourceOwnerPasswordConfigurerCustomizer implements OAuth2AuthorizationServerConfigurerCustomizer{
	private final ApplicationContext context;

	@Override
	public void customize(OAuth2AuthorizationServerConfigurer oAuth2AuthorizationServerConfigurer,
						  HttpSecurity httpSecurity) {
		// 添加 resource owner password 模式支持
		oAuth2AuthorizationServerConfigurer.tokenEndpoint(tokenEndpoint -> {

			UserDetailsService userDetailsService = getBeanOrNull(UserDetailsService.class);
			OAuth2AuthorizationService authorizationService = getBeanOrNull(OAuth2AuthorizationService.class);
			OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils
				.getTokenGenerator(httpSecurity);
			PasswordEncoder passwordEncoder = getBeanOrNull(PasswordEncoder.class);

			OAuth2ResourceOwnerPasswordAuthenticationProvider authenticationProvider = new OAuth2ResourceOwnerPasswordAuthenticationProvider(
				authorizationService, tokenGenerator, userDetailsService);
			if (passwordEncoder != null) {
				authenticationProvider.setPasswordEncoder(passwordEncoder);
			}

			tokenEndpoint.authenticationProvider(authenticationProvider);
			tokenEndpoint.accessTokenRequestConverter(new OAuth2ResourceOwnerPasswordAuthenticationConverter());
		});
	}

	/**
	 * @return a bean of the requested class if there's just a single registered
	 * component, null otherwise.
	 */
	private <T> T getBeanOrNull(Class<T> type) {
		String[] beanNames = this.context.getBeanNamesForType(type);
		if (beanNames.length != 1) {
			return null;
		}
		return this.context.getBean(beanNames[0], type);
	}

}
