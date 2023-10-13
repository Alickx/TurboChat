package com.turbochat.chat.oauth2.server.authorization.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationEndpointConfigurer;

/**
 * @Description
 * @Date 2023/7/19 13:24
 * @Created by Alickx
 */
@Getter
@Setter
@ConfigurationProperties(prefix = OAuth2AuthorizationServerProperties.PREFIX)
public class OAuth2AuthorizationServerProperties {

	public static final String PREFIX = "turbochat.security.oauth2.authorization-server";

	/**
	 * 登录验证码开关
	 */
	private boolean loginCaptchaEnabled = false;

	/**
	 * 开启服务端登录页
	 */
	private boolean loginPageEnabled = false;

	/**
	 * 登录地址
	 * <p>
	 * - 不配置将使用 security 默认的登录页：/login <br>
	 * - 配置后则必须自己提供登录页面
	 */
	private String loginPage = null;

	/**
	 * 用户同意授权页面
	 *
	 * @see OAuth2AuthorizationEndpointConfigurer#consentPage
	 */
	private String consentPage;

	/**
	 * 无状态登录
	 */
	private boolean stateless = false;

}
