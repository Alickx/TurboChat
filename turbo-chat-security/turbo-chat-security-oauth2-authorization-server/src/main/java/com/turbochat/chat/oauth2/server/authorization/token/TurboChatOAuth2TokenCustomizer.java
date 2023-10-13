package com.turbochat.chat.oauth2.server.authorization.token;

import com.turbochat.chat.common.security.constant.TokenAttributeNameConstants;
import com.turbochat.chat.common.security.constant.UserInfoFiledNameConstants;
import com.turbochat.chat.common.security.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Date 2023/7/19 13:26
 * @Created by Alickx
 */
public class TurboChatOAuth2TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {

	@Override
	public void customize(OAuth2TokenClaimsContext context) {
		OAuth2TokenClaimsSet.Builder claims = context.getClaims();
		Authentication authentication = context.getPrincipal();

		// client token
		if (authentication instanceof OAuth2ClientAuthenticationToken) {
			claims.claim(TokenAttributeNameConstants.IS_CLIENT, true);
			return;
		}

		Object principal = authentication.getPrincipal();
		if (principal instanceof User user) {
			Map<String, Object> attributes = user.getAttributes();
			claims.claim(TokenAttributeNameConstants.ATTRIBUTES, attributes);
			HashMap<String, Object> userInfoMap = getUserInfoMap(user);
			claims.claim(TokenAttributeNameConstants.INFO, userInfoMap);
			claims.claim(TokenAttributeNameConstants.IS_CLIENT, false);
		}
	}

	private static HashMap<String, Object> getUserInfoMap(User user) {
		HashMap<String, Object> userInfo = new HashMap<>(6);
		userInfo.put(UserInfoFiledNameConstants.USER_ID, user.getUserId());
		userInfo.put(UserInfoFiledNameConstants.TYPE, user.getType());
		userInfo.put(UserInfoFiledNameConstants.ORGANIZATION_ID, user.getOrganizationId());
		userInfo.put(UserInfoFiledNameConstants.USERNAME, user.getUsername());
		userInfo.put(UserInfoFiledNameConstants.NICKNAME, user.getNickname());
		userInfo.put(UserInfoFiledNameConstants.AVATAR, user.getAvatar());
		userInfo.put(UserInfoFiledNameConstants.EMAIL, user.getEmail());
		userInfo.put(UserInfoFiledNameConstants.GENDER, user.getGender());
		userInfo.put(UserInfoFiledNameConstants.PHONE_NUMBER, user.getPhoneNumber());
		userInfo.put(UserInfoFiledNameConstants.STATUS, user.getStatus());
		return userInfo;
	}


}
