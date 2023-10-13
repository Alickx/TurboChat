package com.turbochat.chat.server.modules.message.message;

import com.turbochat.chat.server.modules.message.constant.enums.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 消息基础类
 * @Date 2023/7/28 1:24
 * @Created by Alickx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseMessage {


	/**
	 * 消息类型
	 * @see MessageTypeEnum
	 */
	private Integer type;


}
