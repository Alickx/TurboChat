package com.turbochat.chat.server.modules.connect.web.handler;

import com.turbochat.chat.server.modules.connect.web.WebsocketProperties;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Description 管理所有的 handler
 * @Date 2023/7/12 20:42
 * @Created by Alickx
 */
@Component
@RequiredArgsConstructor
public class HandlerHolder extends ChannelInitializer<SocketChannel> {

	private final WebsocketProperties websocketProperties;
	private final PointSecurityHandler pointSecurityHandler;
	private final AuthenticationHandler authenticationHandler;
	private final MessageHandler messageHandler;

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {

		ChannelPipeline pipeline = socketChannel.pipeline();
		pipeline.addLast(new HttpServerCodec())
			.addLast(new ChunkedWriteHandler())
			.addLast(new HttpObjectAggregator(65535))
			// 检查连接的是否是设置的端点
			.addLast(pointSecurityHandler)
			.addLast(new WebSocketServerProtocolHandler(websocketProperties.getPath(), null, true, websocketProperties.getMaxFrameSize()))
			.addLast(authenticationHandler)
			.addLast(messageHandler);
	}
}
