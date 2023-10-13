package com.turbochat.chat.server.security.authorizationserver.env;

import com.turbochat.chat.common.security.ScopeNames;
import com.turbochat.chat.server.security.authorizationserver.grant.CustomAuthorizationGrantType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

/**
 * @Description 初始化client环境
 * @Date 2023/7/27 14:52
 * @Created by Alickx
 */
@Component
@RequiredArgsConstructor
public class RegisteredClientInitializer implements ApplicationRunner {

	private final RegisteredClientRepository registeredClientRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		RegisteredClient app = registeredClientRepository.findByClientId("app");

		if (Objects.isNull(app)) {

			RegisteredClient appClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("app")
				.clientSecret("{noop}app")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.PASSWORD)
				.authorizationGrantType(CustomAuthorizationGrantType.MOBILE)
				.scope(ScopeNames.SKIP_CAPTCHA)
				.scope(ScopeNames.SKIP_PASSWORD_DECODE)
				.tokenSettings(TokenSettings.builder()
					// 使用不透明令牌
					.accessTokenFormat(OAuth2TokenFormat.REFERENCE)
					.accessTokenTimeToLive(Duration.ofDays(1))
					.refreshTokenTimeToLive(Duration.ofDays(3))
					.build())
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
				.build();

			registeredClientRepository.save(appClient);

		}
	}
}
