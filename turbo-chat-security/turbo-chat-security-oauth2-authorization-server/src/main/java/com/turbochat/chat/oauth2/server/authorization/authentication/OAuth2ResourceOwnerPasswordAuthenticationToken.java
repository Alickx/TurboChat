package com.turbochat.chat.oauth2.server.authorization.authentication;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Date 2023/7/19 12:02
 * @Created by Alickx
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OAuth2ResourceOwnerPasswordAuthenticationToken extends
	AbstractOAuth2ResourceOwnerAuthenticationToken {

	private final String username;

	@SuppressWarnings("deprecation")
	public OAuth2ResourceOwnerPasswordAuthenticationToken(String username, Authentication clientPrincipal,
														  @Nullable Map<String, Object> additionalParameters, @Nullable Set<String> scopes) {
		super(AuthorizationGrantType.PASSWORD, clientPrincipal, additionalParameters, scopes);
		Assert.hasText(username, "username cannot be empty");
		this.username = username;
	}

	@Override
	public String getName() {
		return username;
	}

}
