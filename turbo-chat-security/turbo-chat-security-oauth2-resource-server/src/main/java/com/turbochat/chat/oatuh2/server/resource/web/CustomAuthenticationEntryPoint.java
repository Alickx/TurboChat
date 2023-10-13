package com.turbochat.chat.oatuh2.server.resource.web;

import com.tuobochat.chat.common.core.result.R;
import com.tuobochat.chat.common.core.result.SystemResultCode;
import com.turbochat.chat.common.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Description
 * @Date 2023/7/19 14:21
 * @Created by Alickx
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
						 AuthenticationException e) throws IOException, ServletException {

		String utf8 = StandardCharsets.UTF_8.toString();

		httpServletResponse.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		httpServletResponse.setCharacterEncoding(utf8);
		httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		R<Object> r = R.failed(SystemResultCode.UNAUTHORIZED, e.getMessage());
		httpServletResponse.getWriter().write(JsonUtil.toJson(r));
	}

}
