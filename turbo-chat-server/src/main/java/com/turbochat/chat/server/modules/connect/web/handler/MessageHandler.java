package com.turbochat.chat.server.modules.connect.web.handler;

import com.turbochat.chat.common.util.JsonUtil;
import com.turbochat.chat.server.modules.connect.ConnectManagerService;
import com.turbochat.chat.server.modules.message.constant.enums.MessageTypeEnum;
import com.turbochat.chat.server.modules.message.message.TextMessage;
import com.turbochat.chat.server.modules.message.send.SendService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description
 * @Date 2023/7/14 1:00
 * @Created by Alickx
 */
@ChannelHandler.Sharable
@Component
@Slf4j
@RequiredArgsConstructor
public class MessageHandler extends ChannelInboundHandlerAdapter {

	private final SendService sendService;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		if (msg instanceof TextWebSocketFrame textWebSocketFrame) {

			String json = textWebSocketFrame.text();

			Map map = JsonUtil.toBean(json, Map.class);
			if (map != null) {

				Integer type = (Integer) map.get("type");

				if (type.equals(MessageTypeEnum.TEXT.getCode())) {

					TextMessage textMessage = JsonUtil.toBean(json, TextMessage.class);
					if (textMessage == null) {
						return;
					}
					sendService.send(textMessage);
				}
			}
		}
		ctx.fireChannelRead(msg);

	}


}
