package com.turbochat.chat.server.modules.connect;

import cn.hutool.core.util.StrUtil;
import com.turbochat.chat.common.redis.RedisHelper;
import com.turbochat.chat.common.util.JsonUtil;
import com.turbochat.chat.server.constant.RedisKeyConstant;
import com.turbochat.chat.server.modules.connect.web.WebsocketChannelManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description 连接管理器实现
 * @Date 2023/7/13 21:49
 * @Created by Alickx
 */
@Service
@Slf4j
public class ConnectManagerService extends AbstractConnectManagerService {


	@Override
	public void online(UserConnect userConnect) {
		Long userId = userConnect.getUserId();
		String platform = userConnect.getPlatform();
		UserConnect connectByCache = this.getUserConnect(userId, platform);
		if (connectByCache != null) {
			log.warn("用户[{}]已经存在连接信息,旧连接信息[{}],新连接信息[{}]", userId, connectByCache, userConnect);
			return;
		}
		String key = RedisKeyConstant.CONNECT_MANAGER + platform;
		RedisHelper.hSet(key, StrUtil.toString(userId), JsonUtil.toJson(userConnect));

		// TODO 用户上线事件
	}

	@Override
	public void offline(UserConnect userConnect) {
		Long userId = userConnect.getUserId();
		String platform = userConnect.getPlatform();
		UserConnect connectByCache = this.getUserConnect(userId, platform);
		if (connectByCache == null) {
			log.warn("用户[{}]不存在连接信息,连接信息[{}]", userId, userConnect);
			return;
		}
		String key = RedisKeyConstant.CONNECT_MANAGER + platform;
		RedisHelper.hDel(key, StrUtil.toString(userId));

		// TODO 用户下线事件
	}

	@Override
	public boolean isOnline(UserConnect userConnect) {
		Long userId = userConnect.getUserId();
		String platform = userConnect.getPlatform();
		UserConnect connectByCache = this.getUserConnect(userId, platform);
		return connectByCache != null;
	}

	/**
	 * 获取用户连接信息
	 * @param userId 用户ID
	 * @param platform 上线平台
	 * @return 用户连接类
	 */
	@Override
	public UserConnect getUserConnect(Long userId, String platform) {
		String key = RedisKeyConstant.CONNECT_MANAGER + platform;
		String value = RedisHelper.hGet(key, StrUtil.toString(userId));
		if (StrUtil.isBlank(value)) {
			return null;
		}
		return JsonUtil.toBean(value, UserConnect.class);
	}
}
