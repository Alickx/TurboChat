package com.turbochat.chat.server.security.authorizationserver.userdetails;

import com.turbochat.chat.server.modules.user.domain.AppUser;
import com.turbochat.chat.server.modules.user.mapper.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Description 用户details服务
 * @Date 2023/7/24 15:45
 * @Created by Alickx
 */
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

	private final AppUserMapper appUserMapper;

	@Override
	public UserDetails loadUserByUsername(String username) {

		AppUser appUser = appUserMapper.queryUserByUserName(username);

		if (Objects.isNull(appUser)) {
			throw new UsernameNotFoundException("用户不存在");
		}

		return UserDetailsConverter.convert(appUser);

	}

	public UserDetails loadUserByPhoneNumber(String phoneNumber) {

		AppUser appUser = appUserMapper.queryUserByPhoneNumber(phoneNumber);

		if (Objects.isNull(appUser)) {
			throw new UsernameNotFoundException("用户不存在");
		}

		return UserDetailsConverter.convert(appUser);

	}

}
