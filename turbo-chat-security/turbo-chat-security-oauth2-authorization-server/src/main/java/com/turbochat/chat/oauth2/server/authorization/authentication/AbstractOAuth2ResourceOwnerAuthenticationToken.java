package com.turbochat.chat.oauth2.server.authorization.authentication;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Date 2023/7/19 11:55
 * @Created by Alickx
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AbstractOAuth2ResourceOwnerAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

	private final Set<String> scopes;

	public AbstractOAuth2ResourceOwnerAuthenticationToken(AuthorizationGrantType authorizationGrantType,
														  Authentication clientPrincipal, @Nullable Map<String, Object> additionalParameters,
														  @Nullable Set<String> scopes) {
		super(authorizationGrantType, clientPrincipal, additionalParameters);
		this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
	}

	public Set<String> getScopes() {
		return this.scopes;
	}

}
