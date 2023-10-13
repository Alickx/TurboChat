package com.turbochat.chat.oauth2.server.authorization.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.Assert;

/**
 * @Description
 * @Date 2023/7/19 12:04
 * @Created by Alickx
 */
@Slf4j
public class OAuth2TokenRevocationAuthenticationProvider implements AuthenticationProvider {

	private final OAuth2AuthorizationService authorizationService;

	/**
	 * Constructs an {@code OAuth2TokenRevocationAuthenticationProvider} using the
	 * provided parameters.
	 * @param authorizationService the authorization service
	 */
	public OAuth2TokenRevocationAuthenticationProvider(OAuth2AuthorizationService authorizationService) {
		Assert.notNull(authorizationService, "authorizationService cannot be null");
		this.authorizationService = authorizationService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		OAuth2TokenRevocationAuthenticationToken tokenRevocationAuthentication = (OAuth2TokenRevocationAuthenticationToken) authentication;

		OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(
			tokenRevocationAuthentication);
		RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

		OAuth2Authorization authorization = this.authorizationService
			.findByToken(tokenRevocationAuthentication.getToken(), null);
		if (authorization == null) {
			if (log.isTraceEnabled()) {
				log.trace("Did not authenticate token revocation request since token was not found");
			}
			// Return the authentication request when token not found
			return tokenRevocationAuthentication;
		}

		if (!registeredClient.getId().equals(authorization.getRegisteredClientId())) {
			throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
		}

		OAuth2Authorization.Token<OAuth2Token> token = authorization.getToken(tokenRevocationAuthentication.getToken());
		authorization = OAuth2AuthenticationProviderUtils.invalidate(authorization, token.getToken());
		this.authorizationService.save(authorization);

		if (log.isTraceEnabled()) {
			log.trace("Saved authorization with revoked token");
			// This log is kept separate for consistency with other providers
			log.trace("Authenticated token revocation request");
		}

		// 返回自定义的 token，携带上注销的 token 对应的 authorization
		return new OAuth2TokenRevocationAuthenticationToken(authorization, token.getToken(), clientPrincipal);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OAuth2TokenRevocationAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
