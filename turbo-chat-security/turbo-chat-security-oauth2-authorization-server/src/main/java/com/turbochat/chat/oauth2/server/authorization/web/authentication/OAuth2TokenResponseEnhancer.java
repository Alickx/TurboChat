package com.turbochat.chat.oauth2.server.authorization.web.authentication;

import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;

import java.util.Map;

/**
 * @Description 令牌响应增强
 * @Date 2023/7/19 13:32
 * @Created by Alickx
 */
@FunctionalInterface
public interface OAuth2TokenResponseEnhancer {

	Map<String,Object> enhance(OAuth2AccessTokenAuthenticationToken accessTokenAuthentication);

}
