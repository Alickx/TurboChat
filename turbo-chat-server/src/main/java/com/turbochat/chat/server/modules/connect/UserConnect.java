package com.turbochat.chat.server.modules.connect;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description 用户连接类
 * @Date 2023/7/13 21:04
 * @Created by Alickx
 */
@Data
@Accessors(chain = true)
public class UserConnect {

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户token
	 */
	private String token;

	/**
	 * 用户ip
	 */
	private String ip;

	/**
	 * 平台
	 */
	private String platform;


}
