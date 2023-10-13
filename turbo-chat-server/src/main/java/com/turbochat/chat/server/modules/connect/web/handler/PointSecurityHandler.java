package com.turbochat.chat.server.modules.connect.web.handler;

import com.turbochat.chat.server.modules.connect.web.WebsocketProperties;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Description 端点保护
 * @Date 2023/7/13 16:07
 * @Created by Alickx
 */
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class PointSecurityHandler extends ChannelInboundHandlerAdapter {

	private final WebsocketProperties websocketProperties;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		if (websocketProperties.getPath().isEmpty()) {
			// 没有设置端点，直接放行
			super.channelRead(ctx, msg);
			return ;
		}

		if (msg instanceof FullHttpRequest fullHttpRequest) {
			String uri = fullHttpRequest.uri();
			if (!uri.equals(websocketProperties.getPath())) {
				// 访问的路径不是 websocket的端点地址，响应404
				ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND))
					.addListener(ChannelFutureListener.CLOSE);
				return ;
			}
		}
		super.channelRead(ctx, msg);
	}
}
