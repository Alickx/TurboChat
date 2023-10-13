package com.turbochat.chat;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.turbochat.chat.server.ServerApplication;
import com.turbochat.chat.server.modules.user.domain.AppUser;
import com.turbochat.chat.server.modules.user.mapper.AppUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description
 * @Date 2023/7/27 16:12
 * @Created by Alickx
 */
@SpringBootTest(classes = ServerApplication.class)
public class RegisterTest {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AppUserMapper appUserMapper;

	@Test
	public void RegisterAppUser() {

		AppUser appUser = new AppUser();
		appUser.setUserName("user1");
		appUser.setUserId(IdUtil.getSnowflakeNextId());
		appUser.setNickName("C端用户1");
		appUser.setPassword(passwordEncoder.encode("a123456"));
		appUser.setPhoneNumber("15800009999");
		appUser.setType(1);

		appUserMapper.insert(appUser);

	}


}
