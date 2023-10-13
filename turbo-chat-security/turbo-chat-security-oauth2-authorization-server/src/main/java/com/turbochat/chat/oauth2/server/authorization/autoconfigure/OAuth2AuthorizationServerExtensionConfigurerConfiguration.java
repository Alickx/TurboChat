package com.turbochat.chat.oauth2.server.authorization.autoconfigure;

import com.turbochat.chat.oauth2.server.authorization.config.configurer.OAuth2LoginCaptchaConfigurer;
import com.turbochat.chat.oauth2.server.authorization.config.configurer.OAuth2LoginPasswordDecoderConfigurer;
import com.turbochat.chat.oauth2.server.authorization.properties.OAuth2AuthorizationServerProperties;
import com.turbochat.chat.security.captcha.CaptchaValidator;
import com.turbochat.chat.security.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Date 2023/7/19 12:14
 * @Created by Alickx
 */
@Configuration(proxyBeanMethods = false)
public class OAuth2AuthorizationServerExtensionConfigurerConfiguration {

	/**
	 * 登录验证码配置
	 * @param captchaValidator 验证码验证器
	 * @return FilterRegistrationBean<LoginCaptchaFilter>
	 */
	@Bean
	@ConditionalOnProperty(prefix = OAuth2AuthorizationServerProperties.PREFIX, name = "login-captcha-enabled",
		havingValue = "true")
	public OAuth2LoginCaptchaConfigurer oAuth2LoginCaptchaConfigurer(CaptchaValidator captchaValidator) {
		return new OAuth2LoginCaptchaConfigurer(captchaValidator);
	}

	/**
	 * password 模式下，密码入参要求 AES 加密。 在进入令牌端点前，通过过滤器进行解密处理。
	 * @param securityProperties 安全配置相关
	 * @return FilterRegistrationBean<LoginPasswordDecoderFilter>
	 */
	@Bean
	@ConditionalOnProperty(prefix = SecurityProperties.PREFIX, name = "password-secret-key")
	public OAuth2LoginPasswordDecoderConfigurer oAuth2LoginPasswordDecoderConfigurer(
		SecurityProperties securityProperties) {
		return new OAuth2LoginPasswordDecoderConfigurer(securityProperties.getPasswordSecretKey());
	}


}
