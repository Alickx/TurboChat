package com.turbochat.chat.server.modules.connect.web;

import com.turbochat.chat.server.modules.connect.ConnectManagerService;
import com.turbochat.chat.server.modules.connect.web.handler.HandlerHolder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @Description Netty 服务端启动类
 * @Date 2023/7/12 15:42
 * @Created by Alickx
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebsocketServerBootstrap implements ApplicationRunner, ApplicationListener<ContextClosedEvent> {

    private Channel serverChannel;
    private final WebsocketProperties websocketProperties;
    private final HandlerHolder handlerHolder;
	private final ConnectManagerService connectManagerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(websocketProperties.getIp(), websocketProperties.getPort()))
                    .childHandler(handlerHolder);
            this.serverChannel = serverBootstrap.bind().sync().channel();
            log.info("TurboChat Websocket 服务端启动成功，监听端口：{}", websocketProperties.getPort());
            serverChannel.closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (this.serverChannel != null) {
            this.serverChannel.close();
			connectManagerService.closeAllConnect();
			log.info("TurboChat 服务端关闭所有连接完成");
        }
        log.info("TurboChat 服务端关闭");
    }

}
