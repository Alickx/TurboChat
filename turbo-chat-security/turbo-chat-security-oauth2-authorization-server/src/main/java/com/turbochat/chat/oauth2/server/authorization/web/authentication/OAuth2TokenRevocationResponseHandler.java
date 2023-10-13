package com.turbochat.chat.oauth2.server.authorization.web.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @Description
 * @Date 2023/7/19 13:33
 * @Created by Alickx
 */
@RequiredArgsConstructor
public class OAuth2TokenRevocationResponseHandler implements AuthenticationSuccessHandler {

	private final ApplicationEventPublisher publisher;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) {
		// 发布用户登出事件
		publisher.publishEvent(new LogoutSuccessEvent(authentication));
		// 返回 200 响应
		response.setStatus(HttpStatus.OK.value());
	}

}
