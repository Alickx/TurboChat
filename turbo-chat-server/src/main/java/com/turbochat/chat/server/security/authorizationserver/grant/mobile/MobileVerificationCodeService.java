package com.turbochat.chat.server.security.authorizationserver.grant.mobile;

/**
 * @Description 手机验证码校验服务
 * @Date 2023/7/24 15:51
 * @Created by Alickx
 */
public class MobileVerificationCodeService {

	/**
	 * 校验手机验证码是否正确
	 */
	public boolean checkVerificationCode(String phoneNumber, String verificationCode) {
		// 这里简单判断验证码是否等于手机号后四位
		// 实际开发应该判断是否等于对应手机号下发的验证码
		int length = phoneNumber.length();
		return phoneNumber.substring(length - 4, length).equals(verificationCode);
	}

}
