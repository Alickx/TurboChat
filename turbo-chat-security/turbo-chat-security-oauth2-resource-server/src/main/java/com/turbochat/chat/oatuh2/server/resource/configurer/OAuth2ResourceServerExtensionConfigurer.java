package com.turbochat.chat.oatuh2.server.resource.configurer;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * @Description
 * @Date 2023/7/19 14:08
 * @Created by Alickx
 */
public abstract class OAuth2ResourceServerExtensionConfigurer<H extends HttpSecurityBuilder<H>>
	extends AbstractHttpConfigurer<OAuth2ResourceServerExtensionConfigurer<H>, H> {


}
