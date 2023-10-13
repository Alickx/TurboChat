package com.turbochat.chat.oatuh2.server.resource.annotation;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;

import java.lang.annotation.*;

/**
 * @Description
 * @Date 2023/7/19 14:03
 * @Created by Alickx
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration(OAuth2ResourceServerAutoConfiguration.class)
public @interface EnableOauth2ResourceServer {
}
