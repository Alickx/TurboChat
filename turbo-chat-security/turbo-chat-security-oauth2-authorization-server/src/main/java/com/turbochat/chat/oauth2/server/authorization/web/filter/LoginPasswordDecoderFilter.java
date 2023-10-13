package com.turbochat.chat.oauth2.server.authorization.web.filter;

import cn.hutool.core.text.CharSequenceUtil;
import com.tuobochat.chat.common.core.result.R;
import com.tuobochat.chat.common.core.result.SystemResultCode;
import com.turbochat.chat.common.security.ScopeNames;
import com.turbochat.chat.common.security.util.PasswordUtils;
import com.turbochat.chat.common.util.JsonUtil;
import com.turbochat.chat.oauth2.server.authorization.authentication.OAuth2AuthenticationProviderUtils;
import com.turbochat.chat.oauth2.server.authorization.web.request.ModifyParamMapRequestWrapper;
import com.turbochat.chat.security.captcha.CaptchaValidateResult;
import com.turbochat.chat.security.captcha.CaptchaValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 登录密码解密过滤器
 * @Date 2023/7/19 13:36
 * @Created by Alickx
 */
@Slf4j
@RequiredArgsConstructor
public class LoginPasswordDecoderFilter extends OncePerRequestFilter {

	private final RequestMatcher requestMatcher;

	private final String passwordSecretKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		if (!this.requestMatcher.matches(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 未配置密码密钥时，直接跳过
		if (passwordSecretKey == null) {
			log.warn("passwordSecretKey not configured, skip password decoder");
			filterChain.doFilter(request, response);
			return;
		}

		// 非密码模式下，直接跳过
		String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
		if (!AuthorizationGrantType.PASSWORD.getValue().equals(grantType)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 获取当前客户端
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(authentication);
		RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

		// 测试客户端密码不加密，直接跳过（swagger 或 postman测试时使用）
		if (registeredClient != null && registeredClient.getScopes().contains(ScopeNames.SKIP_PASSWORD_DECODE)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 解密前台加密后的密码
		Map<String, String[]> parameterMap = new HashMap<>(request.getParameterMap());
		String passwordAes = request.getParameter(OAuth2ParameterNames.PASSWORD);

		try {
			String password = PasswordUtils.decodeAES(passwordAes, passwordSecretKey);
			parameterMap.put(OAuth2ParameterNames.PASSWORD, new String[] { password });
		}
		catch (Exception e) {
			log.error("[doFilterInternal] password decode aes error，passwordAes: {}，passwordSecretKey: {}", passwordAes,
				passwordSecretKey, e);
			response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			R<String> r = R.failed(SystemResultCode.UNAUTHORIZED, "用户名或密码错误！");
			response.getWriter().write(JsonUtil.toJson(r));
			return;
		}

		// SpringSecurity 默认从ParameterMap中获取密码参数
		// 由于原生的request中对parameter加锁了，无法修改，所以使用包装类
		filterChain.doFilter(new ModifyParamMapRequestWrapper(request, parameterMap), response);
	}

}
