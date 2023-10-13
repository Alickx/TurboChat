package com.turbochat.chat.server.modules.user.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description 业务用户实体
 * @Date 2023/7/24 16:24
 * @Created by Alickx
 */
@Data
@Accessors(chain = true)
public class AppUser {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 手机号码
	 */
	private String phoneNumber;

	/**
	 * 用户类型
	 */
	private Integer type;

}
