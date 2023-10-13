package com.turbochat.chat.server.modules.connect.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description Netty配置文件
 * @Date 2023/7/12 15:55
 * @Created by Alickx
 */
@ConfigurationProperties(prefix = "netty")
@Configuration
@Data
public class WebsocketProperties {

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 绑定的网卡
     */
    private String ip;

    /**
     * 消息帧最大体积
     */
    private Long maxFrameSize;

    /**
     * uri路径
     */
    private String path;

}
