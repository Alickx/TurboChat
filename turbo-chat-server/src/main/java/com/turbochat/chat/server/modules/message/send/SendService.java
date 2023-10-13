package com.turbochat.chat.server.modules.message.send;

import com.turbochat.chat.server.modules.connect.ConnectManagerService;
import com.turbochat.chat.server.modules.connect.web.WebsocketChannelManager;
import com.turbochat.chat.server.modules.message.message.BaseMessage;
import com.turbochat.chat.server.modules.message.message.TextMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Date 2023/7/14 11:12
 * @Created by Alickx
 */
@Service
@RequiredArgsConstructor
public class SendService extends AbstractSendService {

	private final ConnectManagerService connectManagerService;

	@Override
	public Boolean send(BaseMessage message) {

		if (message instanceof TextMessage textMessage) {

			boolean contains = WebsocketChannelManager.contains(textMessage.getToUserId());

			if (contains) {
				WebsocketChannelManager.send(textMessage.getToUserId(), textMessage);
				return true;
			} else {
				return false;
			}

		}

		return false;


	}

	@Override
	public Boolean recall() {
		return null;
	}
}
