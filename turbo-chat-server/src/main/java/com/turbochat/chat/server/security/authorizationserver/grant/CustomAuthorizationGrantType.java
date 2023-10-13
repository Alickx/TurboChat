package com.turbochat.chat.server.security.authorizationserver.grant;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * @Description 自定义授权类型字段
 * @Date 2023/7/24 15:49
 * @Created by Alickx
 */
public final class CustomAuthorizationGrantType {

	private CustomAuthorizationGrantType() {}

	public static final AuthorizationGrantType MOBILE = new AuthorizationGrantType("mobile");

}
