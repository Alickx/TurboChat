package com.turbochat.chat.security.captcha;

import lombok.Getter;

/**
 * @Description 验证码的校验结果
 * @Date 2023/7/19 10:57
 * @Created by Alickx
 */
@Getter
public class CaptchaValidateResult {

	private final boolean success;

	private final String message;

	public CaptchaValidateResult(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public static CaptchaValidateResult success() {
		return success("success");
	}

	public static CaptchaValidateResult success(String successMessage) {
		return new CaptchaValidateResult(true, successMessage);
	}

	public static CaptchaValidateResult failure() {
		return failure("captcha validate failure");
	}

	public static CaptchaValidateResult failure(String failureMessage) {
		return new CaptchaValidateResult(false, failureMessage);
	}

}
