package com.turbochat.chat.oauth2.server.authorization.authentication;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;

/**
 * @Description
 * @Date 2023/7/19 11:58
 * @Created by Alickx
 */
@UtilityClass
public class OAuth2AuthenticationProviderUtils {

	public OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(
		Authentication authentication) {
		if (authentication == null) {
			throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
		}
		if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
			return (OAuth2ClientAuthenticationToken) authentication;
		}
		OAuth2ClientAuthenticationToken clientPrincipal = null;
		if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
			clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
		}
		if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
			return clientPrincipal;
		}
		throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
	}

	public <T extends OAuth2Token> OAuth2Authorization invalidate(OAuth2Authorization authorization, T token) {

		OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.from(authorization)
			.token(token, metadata -> metadata.put(OAuth2Authorization.Token.INVALIDATED_METADATA_NAME, true));

		if (OAuth2RefreshToken.class.isAssignableFrom(token.getClass())) {
			authorizationBuilder.token(
				authorization.getAccessToken().getToken(),
				metadata -> metadata.put(OAuth2Authorization.Token.INVALIDATED_METADATA_NAME, true)
			);

			OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode =
				authorization.getToken(OAuth2AuthorizationCode.class);
			if (authorizationCode != null && !authorizationCode.isInvalidated()) {
				authorizationBuilder.token(
					authorizationCode.getToken(),
					metadata -> metadata.put(OAuth2Authorization.Token.INVALIDATED_METADATA_NAME, true)
				);
			}
		}

		return authorizationBuilder.build();
	}

}
