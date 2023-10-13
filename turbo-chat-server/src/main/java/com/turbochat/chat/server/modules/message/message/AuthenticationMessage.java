package com.turbochat.chat.server.modules.message.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 认证消息
 * @Date 2023/7/28 1:39
 * @Created by Alickx
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthenticationMessage extends BaseMessage {

	/**
	 * 用户access_token
	 */
	private String accessToken;


}
