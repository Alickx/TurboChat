package com.turbochat.chat.server.security.authorizationserver.userdetails;

import com.turbochat.chat.common.security.userdetails.User;
import com.turbochat.chat.server.modules.user.domain.AppUser;
import lombok.experimental.UtilityClass;

import java.util.HashMap;

/**
 * @Description
 * @Date 2023/7/27 16:06
 * @Created by Alickx
 */
@UtilityClass
public class UserDetailsConverter {


	public User convert(AppUser appUser) {
		return User.builder()
			.userId(appUser.getUserId())
			.username(appUser.getUserName())
			.nickname(appUser.getNickName())
			.password(appUser.getPassword())
			.organizationId(null)
			.phoneNumber(appUser.getPhoneNumber())
			.type(appUser.getType())
			.attributes(new HashMap<>())
			.status(1)
			.build();
	}

}
