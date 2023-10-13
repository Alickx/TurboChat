package com.turbochat.chat.server.modules.connect.web;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description websocket 连接管理器
 * @Date 2023/7/14 1:05
 * @Created by Alickx
 */
public class WebsocketChannelManager {

	public static final ConcurrentHashMap<Long, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();

	public static void add(Long userId, Channel channel) {
		CHANNEL_MAP.put(userId, channel);
	}

	public static void remove(Long userId) {
		CHANNEL_MAP.remove(userId);
	}

	public static Channel get(Long userId) {
		return CHANNEL_MAP.get(userId);
	}

	public static boolean contains(Long userId) {
		return CHANNEL_MAP.containsKey(userId);
	}

	public static List<Long> getOnlineUserIds() {
		List<Long> userIds = new ArrayList<>();
		CHANNEL_MAP.forEach((userId, channel) -> {
			userIds.add(userId);
		});
		return userIds;
	}

	public static void send(Long userId, Object message) {
		Channel channel = CHANNEL_MAP.get(userId);
		if (channel != null) {
			channel.writeAndFlush(message);
		}
	}

}
