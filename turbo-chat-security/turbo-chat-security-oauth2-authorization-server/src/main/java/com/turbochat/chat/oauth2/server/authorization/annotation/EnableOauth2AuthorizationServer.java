package com.turbochat.chat.oauth2.server.authorization.annotation;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerAutoConfiguration;

import java.lang.annotation.*;

/**
 * @Description 开启 Oauth2 授权服务器
 * @Date 2023/7/19 11:52
 * @Created by Alickx
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration(OAuth2AuthorizationServerAutoConfiguration.class)
public @interface EnableOauth2AuthorizationServer {
}
