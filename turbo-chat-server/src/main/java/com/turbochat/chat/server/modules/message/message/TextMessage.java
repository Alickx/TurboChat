package com.turbochat.chat.server.modules.message.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Date 2023/7/29 1:34
 * @Created by Alickx
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TextMessage extends BaseMessage {

	/**
	 * 文本
	 */
	private String content;

	/**
	 * 收件人id
	 */
	private Long toUserId;


}
