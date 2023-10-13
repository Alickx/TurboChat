package com.turbochat.chat.oauth2.server.authorization.config.configurer;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * @Description
 * @Date 2023/7/19 12:19
 * @Created by Alickx
 */
public abstract class OAuth2AuthorizationServerExtensionConfigurer<C extends OAuth2AuthorizationServerExtensionConfigurer<C, H>, H extends HttpSecurityBuilder<H>>
	extends AbstractHttpConfigurer<C, H> {

}
