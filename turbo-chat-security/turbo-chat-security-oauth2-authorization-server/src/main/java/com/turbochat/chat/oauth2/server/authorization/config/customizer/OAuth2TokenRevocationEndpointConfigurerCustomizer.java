package com.turbochat.chat.oauth2.server.authorization.config.customizer;

import com.turbochat.chat.oauth2.server.authorization.authentication.OAuth2TokenRevocationAuthenticationProvider;
import com.turbochat.chat.oauth2.server.authorization.web.authentication.OAuth2TokenRevocationResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;

/**
 * @Description
 * @Date 2023/7/19 13:23
 * @Created by Alickx
 */
@RequiredArgsConstructor
public class OAuth2TokenRevocationEndpointConfigurerCustomizer implements OAuth2AuthorizationServerConfigurerCustomizer{
	private final OAuth2AuthorizationService authorizationService;

	private final OAuth2TokenRevocationResponseHandler oAuth2TokenRevocationResponseHandler;

	@Override
	public void customize(OAuth2AuthorizationServerConfigurer oAuth2AuthorizationServerConfigurer,
						  HttpSecurity httpSecurity) {
		oAuth2AuthorizationServerConfigurer.tokenRevocationEndpoint(
			tokenRevocation -> tokenRevocation.revocationResponseHandler(oAuth2TokenRevocationResponseHandler)
				.authenticationProvider(new OAuth2TokenRevocationAuthenticationProvider(authorizationService)));
	}
}
