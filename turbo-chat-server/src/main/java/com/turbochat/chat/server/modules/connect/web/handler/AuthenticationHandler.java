package com.turbochat.chat.server.modules.connect.web.handler;

import cn.hutool.core.util.StrUtil;
import com.tuobochat.chat.common.core.constant.enums.ErrorCodeEnum;
import com.tuobochat.chat.common.core.exception.BusinessException;
import com.turbochat.chat.common.security.userdetails.User;
import com.turbochat.chat.common.util.JsonUtil;
import com.turbochat.chat.server.constant.enums.PlatformEnum;
import com.turbochat.chat.server.modules.connect.ConnectManagerService;
import com.turbochat.chat.server.modules.connect.UserConnect;
import com.turbochat.chat.server.modules.connect.web.WebsocketChannelManager;
import com.turbochat.chat.server.modules.message.constant.enums.MessageTypeEnum;
import com.turbochat.chat.server.modules.message.message.AuthenticationMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Objects;

/**
 * @Description 认证处理器
 * @Date 2023/7/13 20:38
 * @Created by Alickx
 */
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
@Slf4j
public class AuthenticationHandler extends ChannelInboundHandlerAdapter {


	private final OpaqueTokenIntrospector opaqueTokenIntrospector;

	private final ConnectManagerService connectManagerService;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		if (msg instanceof TextWebSocketFrame textWebSocketFrame) {

			String json = textWebSocketFrame.text();
			Map map = JsonUtil.toBean(json, Map.class);
			if (map != null) {
				Integer type = (Integer) map.get("type");
				if (type == null || !type.equals(MessageTypeEnum.AUTH.getCode())) {
					ctx.fireChannelRead(msg);
				}
			}

			AuthenticationMessage authenticationMessage = JsonUtil.toBean(json, AuthenticationMessage.class);
			String accessToken = null;
			if (authenticationMessage != null) {
				accessToken = authenticationMessage.getAccessToken();
			}

			if (StrUtil.isBlank(accessToken)) {
				// 抛出异常
				throw new BusinessException(ErrorCodeEnum.PARAM_ERROR, "认证参数不能为空");
			}

			// 校验accessToken是否合法
			OAuth2AuthenticatedPrincipal principal = opaqueTokenIntrospector.introspect(accessToken);

			if (Objects.isNull(principal)) {
				throw new BusinessException(ErrorCodeEnum.PARAM_ERROR, "认证消息错误");
			}

			// 认证成功并加入连接管理
			UserConnect userConnect = getUserConnect(ctx, principal, accessToken);
			WebsocketChannelManager.add(userConnect.getUserId(),ctx.channel());
			connectManagerService.online(userConnect);

		}

		ctx.fireChannelRead(msg);

	}


	public UserConnect getUserConnect(ChannelHandlerContext ctx, OAuth2AuthenticatedPrincipal principal, String accessToken) {

		// 获取ip
		InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
		String hostAddress = inetSocketAddress.getAddress().getHostAddress();

		User user = (User) principal;

		UserConnect userConnect = new UserConnect();
		return userConnect.setIp(hostAddress)
			.setPlatform(PlatformEnum.WEB.getDescription())
			.setUserId(user.getUserId())
			.setUserName(user.getUsername())
			.setToken(accessToken);
	}

}
