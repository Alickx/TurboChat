package com.turbochat.chat.security.captcha;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @Description 验证码验证器
 * @Date 2023/7/19 10:57
 * @Created by Alickx
 */
public interface CaptchaValidator {

	CaptchaValidateResult validate(HttpServletRequest request);

}
