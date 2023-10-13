package com.turbochat.chat.server.modules.connect;

/**
 * @Description 连接管理器接口
 * @Date 2023/7/13 20:33
 * @Created by Alickx
 */
public interface ConnectManager {

	/**
	 * 用户上线
	 * @param userConnect 用户连接信息
	 */
	void online(UserConnect userConnect);

	/**
	 * 用户下线
	 * @param userConnect 用户连接信息
	 */
	void offline(UserConnect userConnect);

	/**
	 * 用户是否在线
	 * @param userConnect 用户连接信息
	 * @return true:在线 false:离线
	 */
	boolean isOnline(UserConnect userConnect);

	/**
	 * 获取用户连接信息
	 * @param userId 用户ID
	 * @param platform 上线平台
	 * @return 用户连接信息
	 */
	UserConnect getUserConnect(Long userId, String platform);

}
