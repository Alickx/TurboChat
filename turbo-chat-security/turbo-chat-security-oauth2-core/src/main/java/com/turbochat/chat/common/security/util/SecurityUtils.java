package com.turbochat.chat.common.security.util;

import com.turbochat.chat.common.security.userdetails.ClientPrincipal;
import com.turbochat.chat.common.security.userdetails.User;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Description 安全工具类
 * @Date 2023/7/19 11:45
 * @Created by Alickx
 */
@UtilityClass
public class SecurityUtils {


	/**
	 * 获取Authentication
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取系统用户Details
	 *
	 * @param authentication 令牌
	 * @return User
	 * <p>
	 */
	public User getUser(Authentication authentication) {
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		}
		return null;
	}

	/**
	 * 获取用户详情
	 */
	public User getUser() {
		Authentication authentication = getAuthentication();
		return getUser(authentication);
	}

	/**
	 * 获取客户端信息
	 */
	public ClientPrincipal getClientPrincipal() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof ClientPrincipal) {
			return (ClientPrincipal) principal;
		}
		return null;
	}


}
