package com.turbochat.chat.oauth2.server.authorization.web.context;


import com.turbochat.chat.oauth2.server.authorization.web.CookieBearerTokenResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.StringUtils;

/**
 * @Description
 * @Date 2023/7/19 13:34
 * @Created by Alickx
 */
public class OAuth2SecurityContextRepository implements SecurityContextRepository {


	private final BearerTokenResolver bearerTokenResolver;

	private final OAuth2AuthorizationService authorizationService;

	public OAuth2SecurityContextRepository(OAuth2AuthorizationService authorizationService) {
		this.bearerTokenResolver = new CookieBearerTokenResolver();
		this.authorizationService = authorizationService;
	}

	public OAuth2SecurityContextRepository(BearerTokenResolver bearerTokenResolver,
										   OAuth2AuthorizationService authorizationService) {
		this.bearerTokenResolver = bearerTokenResolver;
		this.authorizationService = authorizationService;
	}

	@Override
	public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
		HttpServletRequest request = requestResponseHolder.getRequest();
		String bearerToken = this.bearerTokenResolver.resolve(request);
		if (!StringUtils.hasText(bearerToken)) {
			return SecurityContextHolder.createEmptyContext();
		}
		OAuth2Authorization oAuth2Authorization = authorizationService.findByToken(bearerToken,
			OAuth2TokenType.ACCESS_TOKEN);
		if (oAuth2Authorization == null) {
			return SecurityContextHolder.createEmptyContext();
		}
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) oAuth2Authorization
			.getAttributes()
			.get("java.security.Principal");
		return new SecurityContextImpl(usernamePasswordAuthenticationToken);
	}

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {

	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		return this.loadDeferredContext(request) != null;
	}

}
