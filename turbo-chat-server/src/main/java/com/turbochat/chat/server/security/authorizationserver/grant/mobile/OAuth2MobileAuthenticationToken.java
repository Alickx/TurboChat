package com.turbochat.chat.server.security.authorizationserver.grant.mobile;

import com.turbochat.chat.oauth2.server.authorization.authentication.AbstractOAuth2ResourceOwnerAuthenticationToken;
import com.turbochat.chat.server.security.authorizationserver.grant.CustomAuthorizationGrantType;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Date 2023/7/24 15:55
 * @Created by Alickx
 */
public class OAuth2MobileAuthenticationToken extends AbstractOAuth2ResourceOwnerAuthenticationToken {

	private final String phoneNumber;

	public OAuth2MobileAuthenticationToken(String phoneNumber, Authentication clientPrincipal,
										   @Nullable Map<String, Object> additionalParameters, @Nullable Set<String> scopes) {
		super(CustomAuthorizationGrantType.MOBILE, clientPrincipal, additionalParameters, scopes);
		Assert.hasText(phoneNumber, "phone number cannot be empty");
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String getName() {
		return phoneNumber;
	}


}
