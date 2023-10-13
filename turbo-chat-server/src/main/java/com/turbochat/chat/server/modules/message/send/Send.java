package com.turbochat.chat.server.modules.message.send;

import com.turbochat.chat.server.modules.message.message.BaseMessage;

/**
 * @Description 发送接口
 * @Date 2023/7/14 11:08
 * @Created by Alickx
 */
public interface Send {

	/**
	 * 发送消息
	 * @param message 消息
	 * @return 是否发送成功
	 */
	Boolean send(BaseMessage message);

	Boolean recall();



}
