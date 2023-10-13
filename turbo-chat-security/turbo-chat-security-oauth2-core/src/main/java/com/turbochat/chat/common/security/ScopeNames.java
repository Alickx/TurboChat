package com.turbochat.chat.common.security;

/**
 * @Description
 * @Date 2023/7/19 11:48
 * @Created by Alickx
 */
public class ScopeNames {

	private ScopeNames() {
	}

	/**
	 * 跳过验证码
	 */
	public static final String SKIP_CAPTCHA = "skip_captcha";

	/**
	 * 跳过密码解密 （使用明文传输）
	 */
	public static final String SKIP_PASSWORD_DECODE = "skip_password_decode";

}
