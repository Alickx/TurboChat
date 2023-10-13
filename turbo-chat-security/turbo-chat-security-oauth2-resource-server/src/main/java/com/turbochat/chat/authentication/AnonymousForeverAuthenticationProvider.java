package com.turbochat.chat.authentication;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.PathContainer;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Description 同时支持匿名登录和授权登录
 * @Date 2023/7/19 13:59
 * @Created by Alickx
 */
@Slf4j
public class AnonymousForeverAuthenticationProvider implements AuthenticationProvider {


	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

	private final String key;

	private final Object principal;

	private final List<GrantedAuthority> authorities;

	private final List<PathPattern> pathPatterns;

	public AnonymousForeverAuthenticationProvider(List<String> pathList) {
		if (CollectionUtils.isEmpty(pathList)) {
			pathPatterns = new ArrayList<>();
		}
		else {
			pathPatterns = pathList.stream().map(PathPatternParser.defaultInstance::parse).collect(Collectors.toList());
		}

		this.key = UUID.randomUUID().toString();
		this.principal = "anonymousUser";
		this.authorities = AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS");
	}

	public AnonymousForeverAuthenticationProvider(String key, Object principal, List<GrantedAuthority> authorities,
												  List<PathPattern> pathPatterns) {
		this.key = key;
		this.principal = principal;
		this.authorities = authorities;
		this.pathPatterns = pathPatterns;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		HttpServletRequest request = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
			.map(x -> (ServletRequestAttributes) x)
			.map(ServletRequestAttributes::getRequest)
			.orElse(null);
		if (request == null) {
			return null;
		}

		String requestUri = request.getRequestURI();
		PathContainer pathContainer = PathContainer.parsePath(requestUri);

		boolean anyMatch = pathPatterns.stream().anyMatch(x -> x.matches(pathContainer));
		if (anyMatch) {
			Authentication anonymousAuthentication = createAuthentication(request);
			log.debug("Set SecurityContextHolder to anonymous SecurityContext");
			return anonymousAuthentication;
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

	protected Authentication createAuthentication(HttpServletRequest request) {
		AnonymousAuthenticationToken token = new AnonymousAuthenticationToken(this.key, this.principal,
			this.authorities);
		token.setDetails(this.authenticationDetailsSource.buildDetails(request));
		return token;
	}

	public void setAuthenticationDetailsSource(
		AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
		Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

}
