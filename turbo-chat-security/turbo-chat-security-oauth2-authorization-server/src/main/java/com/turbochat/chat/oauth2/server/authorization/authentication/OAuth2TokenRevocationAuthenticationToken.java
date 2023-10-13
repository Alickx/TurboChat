package com.turbochat.chat.oauth2.server.authorization.authentication;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.util.SpringAuthorizationServerVersion;
import org.springframework.util.Assert;

import java.util.Collections;

/**
 * @Description
 * @Date 2023/7/19 12:05
 * @Created by Alickx
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OAuth2TokenRevocationAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = SpringAuthorizationServerVersion.SERIAL_VERSION_UID;

	private final OAuth2Authorization authorization;

	private final String token;

	private final Authentication clientPrincipal;

	private final String tokenTypeHint;

	/**
	 * Constructs an {@code OAuth2TokenRevocationAuthenticationToken} using the provided
	 * parameters.
	 * @param token the token
	 * @param clientPrincipal the authenticated client principal
	 * @param tokenTypeHint the token type hint
	 */
	public OAuth2TokenRevocationAuthenticationToken(String token, Authentication clientPrincipal,
													@Nullable String tokenTypeHint) {
		super(Collections.emptyList());
		Assert.hasText(token, "token cannot be empty");
		Assert.notNull(clientPrincipal, "clientPrincipal cannot be null");
		this.token = token;
		this.clientPrincipal = clientPrincipal;
		this.tokenTypeHint = tokenTypeHint;
		this.authorization = null;
	}

	/**
	 * Constructs an {@code OAuth2TokenRevocationAuthenticationToken} using the provided
	 * parameters.
	 * @param revokedToken the revoked token
	 * @param clientPrincipal the authenticated client principal
	 */
	public OAuth2TokenRevocationAuthenticationToken(OAuth2Authorization authorization, OAuth2Token revokedToken,
													Authentication clientPrincipal) {
		super(Collections.emptyList());
		Assert.notNull(authorization, "authorization cannot be null");
		Assert.notNull(revokedToken, "revokedToken cannot be null");
		Assert.notNull(clientPrincipal, "clientPrincipal cannot be null");
		this.authorization = authorization;
		this.token = revokedToken.getTokenValue();
		this.clientPrincipal = clientPrincipal;
		this.tokenTypeHint = null;
		setAuthenticated(true); // Indicates that the token was authenticated and revoked
	}

	@Override
	public Object getPrincipal() {
		return this.clientPrincipal;
	}

	@Override
	public Object getCredentials() {
		return "";
	}

	/**
	 * Returns the token.
	 * @return the token
	 */
	public String getToken() {
		return this.token;
	}

	/**
	 * Returns the token type hint.
	 * @return the token type hint
	 */
	@Nullable
	public String getTokenTypeHint() {
		return this.tokenTypeHint;
	}

	public OAuth2Authorization getAuthorization() {
		return authorization;
	}

}
