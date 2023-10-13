package com.turbochat.chat.server;

import com.turbochat.chat.oatuh2.server.resource.annotation.EnableOauth2ResourceServer;
import com.turbochat.chat.oauth2.server.authorization.annotation.EnableOauth2AuthorizationServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description TurboChat Server 启动类
 * @Date 2023/7/12 15:21
 * @Created by Alickx
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.turbochat.chat.*.*"})
@MapperScan(basePackages = {"com.turbochat.chat.*.modules.*.mapper"})
@EnableOauth2AuthorizationServer
@EnableOauth2ResourceServer
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
