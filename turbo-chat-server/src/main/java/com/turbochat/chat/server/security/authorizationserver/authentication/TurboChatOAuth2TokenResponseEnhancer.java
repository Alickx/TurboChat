package com.turbochat.chat.server.security.authorizationserver.authentication;

import com.turbochat.chat.common.security.userdetails.User;
import com.turbochat.chat.oauth2.server.authorization.web.authentication.OAuth2TokenResponseEnhancer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Description 令牌返回增强
 * @Date 2023/7/24 15:39
 * @Created by Alickx
 */
@Component
public class TurboChatOAuth2TokenResponseEnhancer implements OAuth2TokenResponseEnhancer {


	@Override
	public Map<String, Object> enhance(OAuth2AccessTokenAuthenticationToken accessTokenAuthentication) {

		Object principal = Optional.ofNullable(SecurityContextHolder.getContext())
			.map(SecurityContext::getAuthentication)
			.map(Authentication::getPrincipal)
			.orElse(null);

		// token 附属信息
		Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();
		if (additionalParameters == null) {
			additionalParameters = new HashMap<>(8);
		}

		// 在 token 响应中添加额外的数据，这里添加了 nickname
		if (principal instanceof User user) {
			Map<String,Object> map = new HashMap<>();
			map.put("nickname",user.getNickname());
			map.put("avatar",user.getAvatar());
			map.put("userId",user.getUserId());
			map.put("gender",user.getGender());
			additionalParameters.put("user_info",map);
		}

		return additionalParameters;

	}
}
