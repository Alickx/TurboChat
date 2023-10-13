package com.turbochat.chat.server.modules.message.constant.enums;

import com.tuobochat.chat.common.core.constant.enums.PowerfulEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Description
 * @Date 2023/7/14 1:14
 * @Created by Alickx
 */
@Getter
@AllArgsConstructor
@ToString
public enum MessageTypeEnum implements PowerfulEnum {

	/**
	 * 认证消息
	 */
	AUTH(10, "认证消息"),

	/**
	 * 心跳消息
	 */
	HEARTBEAT(20, "心跳消息"),

	/**
	 * 文本消息
	 */
	TEXT(30, "文本消息"),

	/**
	 * 图片消息
	 */
	IMAGE(40, "图片消息"),

	/**
	 * 语音消息
	 */
	VOICE(50, "语音消息"),





	;

	private final Integer code;

	private final String description;

}
