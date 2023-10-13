package com.turbochat.chat.common.security.authentication;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @Description 身份验证令牌
 * @Date 2023/7/19 11:04
 * @Created by Alickx
 */
@EqualsAndHashCode(callSuper = true)
public class OAuth2UserAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;

	public OAuth2UserAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		Assert.notNull(principal, "principal cannot be null");
		this.principal = principal;
		setAuthenticated(true);
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public Object getCredentials() {
		return "";
	}

}
