package com.turbochat.chat.oauth2.server.authorization.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.Assert;

/**
 * @Description
 * @Date 2023/7/19 13:28
 * @Created by Alickx
 */
public class CookieBearerTokenResolver implements BearerTokenResolver {

	private String cookieName = OAuth2TokenType.ACCESS_TOKEN.getValue();

	public CookieBearerTokenResolver() {
	}

	public CookieBearerTokenResolver(String cookieName) {
		Assert.hasText(cookieName, "cookie name cannot be empty");
		this.cookieName = cookieName;
	}

	@Override
	public String resolve(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (this.cookieName.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}

		return null;
	}

}
