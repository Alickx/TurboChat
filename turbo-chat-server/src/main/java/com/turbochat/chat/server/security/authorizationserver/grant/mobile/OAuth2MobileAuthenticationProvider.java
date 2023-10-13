package com.turbochat.chat.server.security.authorizationserver.grant.mobile;

import com.turbochat.chat.common.security.authentication.OAuth2UserAuthenticationToken;
import com.turbochat.chat.oauth2.server.authorization.authentication.AbstractOAuth2ResourceOwnerAuthenticationProvider;
import com.turbochat.chat.server.security.authorizationserver.grant.CustomOAuth2ParameterNames;
import com.turbochat.chat.server.security.authorizationserver.userdetails.AppUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;

/**
 * @Description
 * @Date 2023/7/24 15:56
 * @Created by Alickx
 */
@Slf4j
public class OAuth2MobileAuthenticationProvider extends AbstractOAuth2ResourceOwnerAuthenticationProvider<OAuth2MobileAuthenticationToken> {

	private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

	private final AppUserDetailsService userDetailsService;

	private final MobileVerificationCodeService mobileVerificationCodeService;

	public OAuth2MobileAuthenticationProvider(OAuth2AuthorizationService authorizationService,
											  OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator, AppUserDetailsService userDetailsService,
											  MobileVerificationCodeService mobileVerificationCodeService) {
		super(authorizationService, tokenGenerator);
		Assert.notNull(userDetailsService, "userDetailsService cannot be null");
		this.userDetailsService = userDetailsService;
		this.mobileVerificationCodeService = mobileVerificationCodeService;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OAuth2MobileAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	protected Authentication getAuthenticatedAuthentication(OAuth2MobileAuthenticationToken authentication)
		throws AuthenticationException {

		Map<String, Object> additionalParameters = authentication.getAdditionalParameters();

		String phoneNumber = (String) additionalParameters.get(CustomOAuth2ParameterNames.PHONE_NUMBER);
		String verificationCode = (String) additionalParameters.get(CustomOAuth2ParameterNames.VERIFICATION_CODE);

		if (!mobileVerificationCodeService.checkVerificationCode(phoneNumber, verificationCode)) {
			OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "手机验证码错误.", ERROR_URI);
			throw new OAuth2AuthenticationException(error);
		}

		UserDetails userDetails;
		try {
			userDetails = userDetailsService.loadUserByPhoneNumber(phoneNumber);
		}
		catch (UsernameNotFoundException ex) {
			OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "用户不存在.", ERROR_URI);
			throw new OAuth2AuthenticationException(error);
		}

		return new OAuth2UserAuthenticationToken(userDetails,
			Collections.emptyList());
	}

}
