package com.turbochat.chat.server.security.authorizationserver.grant.mobile;

import com.turbochat.chat.oauth2.server.authorization.web.authentication.OAuth2EndpointUtils;
import com.turbochat.chat.server.security.authorizationserver.grant.CustomAuthorizationGrantType;
import com.turbochat.chat.server.security.authorizationserver.grant.CustomOAuth2ParameterNames;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description 自定义手机号登录支持
 * @Date 2023/7/24 15:52
 * @Created by Alickx
 */
public class OAuth2MobileAuthenticationConverter implements AuthenticationConverter {


	String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

	@Override
	public Authentication convert(HttpServletRequest request) {
		// grant_type (REQUIRED)
		String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
		if (!CustomAuthorizationGrantType.MOBILE.getValue().equals(grantType)) {
			return null;
		}

		MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);

		// scope (OPTIONAL)
		Set<String> scopes = null;
		String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
		if (StringUtils.hasText(scope) && parameters.get(OAuth2ParameterNames.SCOPE).size() != 1) {
			OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.SCOPE,
				ACCESS_TOKEN_REQUEST_ERROR_URI);
		}
		if (StringUtils.hasText(scope)) {
			scopes = new HashSet<>(Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
		}

		// mobile (REQUIRED)
		String mobile = parameters.getFirst(CustomOAuth2ParameterNames.PHONE_NUMBER);
		if (!StringUtils.hasText(mobile) || parameters.get(CustomOAuth2ParameterNames.PHONE_NUMBER).size() != 1) {
			OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, CustomOAuth2ParameterNames.PHONE_NUMBER,
				ACCESS_TOKEN_REQUEST_ERROR_URI);
		}

		// verificationCode (REQUIRED)
		String verificationCode = parameters.getFirst(CustomOAuth2ParameterNames.VERIFICATION_CODE);
		if (!StringUtils.hasText(verificationCode)
			|| parameters.get(CustomOAuth2ParameterNames.VERIFICATION_CODE).size() != 1) {
			OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST,
				CustomOAuth2ParameterNames.VERIFICATION_CODE, ACCESS_TOKEN_REQUEST_ERROR_URI);
		}

		Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
		if (clientPrincipal == null) {
			OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ErrorCodes.INVALID_CLIENT,
				ACCESS_TOKEN_REQUEST_ERROR_URI);
		}

		Map<String, Object> additionalParameters = parameters.entrySet()
			.stream()
			.filter(e -> !e.getKey().equals(OAuth2ParameterNames.GRANT_TYPE)
				&& !e.getKey().equals(OAuth2ParameterNames.SCOPE))
			.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

		return new OAuth2MobileAuthenticationToken(mobile, clientPrincipal, additionalParameters, scopes);

	}

}
