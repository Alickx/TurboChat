package com.turbochat.chat.server.modules.connect;

import com.turbochat.chat.common.redis.RedisHelper;
import com.turbochat.chat.server.constant.RedisKeyConstant;
import com.turbochat.chat.server.constant.enums.PlatformEnum;
import com.turbochat.chat.server.modules.connect.web.WebsocketChannelManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description
 * @Date 2023/7/13 23:27
 * @Created by Alickx
 */
@Slf4j
public abstract class AbstractConnectManagerService implements ConnectManager {

	public void closeAllConnect() {
		List<Long> onlineUserIds = WebsocketChannelManager.getOnlineUserIds();
		// remove all connect
		String key = RedisKeyConstant.CONNECT_MANAGER + PlatformEnum.WEB.getDescription();
		List<String> idsToString = onlineUserIds.stream().map(String::valueOf).toList();
		RedisHelper.hDel(key, idsToString.toArray(new String[0]));
	}

}
