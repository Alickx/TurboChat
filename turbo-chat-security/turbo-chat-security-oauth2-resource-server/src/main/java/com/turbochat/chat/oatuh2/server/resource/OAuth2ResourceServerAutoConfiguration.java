package com.turbochat.chat.oatuh2.server.resource;

import com.turbochat.chat.common.security.component.CustomPermissionEvaluator;
import com.turbochat.chat.oatuh2.server.resource.configurer.OAuth2ResourceServerConfigurerCustomizer;
import com.turbochat.chat.oatuh2.server.resource.configurer.OAuth2ResourceServerExtensionConfigurer;
import com.turbochat.chat.oatuh2.server.resource.configurer.Oauth2ResourceServerSecurityFilterChainBuilder;
import com.turbochat.chat.oatuh2.server.resource.configurer.TurboChatOauth2ResourceServerSecurityFilterChainBuilder;
import com.turbochat.chat.oatuh2.server.resource.introspection.TurboChatRemoteOpaqueTokenIntrospector;
import com.turbochat.chat.oatuh2.server.resource.properties.OAuth2ResourceServerProperties;
import com.turbochat.chat.oatuh2.server.resource.web.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * @Description
 * @Date 2023/7/19 14:22
 * @Created by Alickx
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableMethodSecurity
@EnableConfigurationProperties(OAuth2ResourceServerProperties.class)
public class OAuth2ResourceServerAutoConfiguration {


	public static final String OAUTH2_RESOURCE_SERVER_SECURITY_FILTER_CHAIN_BEAN_NAME = "oauth2ResourceServerSecurityFilterChain";

	/**
	 * 资源服务器的过滤器链构建器
	 */
	@Bean
	@ConditionalOnMissingBean(name = OAUTH2_RESOURCE_SERVER_SECURITY_FILTER_CHAIN_BEAN_NAME,
		value = Oauth2ResourceServerSecurityFilterChainBuilder.class)
	public Oauth2ResourceServerSecurityFilterChainBuilder oauth2ResourceServerSecurityFilterChainBuilder(
		OAuth2ResourceServerProperties oAuth2ResourceServerProperties,
		AuthenticationEntryPoint authenticationEntryPoint, BearerTokenResolver bearerTokenResolver,
		ObjectProvider<List<OAuth2ResourceServerConfigurerCustomizer>> configurerCustomizersProvider,
		ObjectProvider<List<OAuth2ResourceServerExtensionConfigurer<HttpSecurity>>> extensionConfigurersProvider) {
		return new TurboChatOauth2ResourceServerSecurityFilterChainBuilder(oAuth2ResourceServerProperties,
			authenticationEntryPoint, bearerTokenResolver, configurerCustomizersProvider,
			extensionConfigurersProvider);
	}

	/**
	 * OAuth2 授权服务器的安全过滤器链，如果和资源服务器共存，需要将其放在资源服务器之前
	 */
	@Bean(name = OAUTH2_RESOURCE_SERVER_SECURITY_FILTER_CHAIN_BEAN_NAME)
	@Order(99)
	@ConditionalOnMissingBean(name = OAUTH2_RESOURCE_SERVER_SECURITY_FILTER_CHAIN_BEAN_NAME)
	public SecurityFilterChain oauth2ResourceServerSecurityFilterChain(
		Oauth2ResourceServerSecurityFilterChainBuilder builder, HttpSecurity httpSecurity) throws Exception {
		return builder.build(httpSecurity);
	}

	/**
	 * 自定义的权限判断组件
	 * @return CustomPermissionEvaluator
	 */
	@Bean(name = "per")
	@ConditionalOnMissingBean(CustomPermissionEvaluator.class)
	public CustomPermissionEvaluator customPermissionEvaluator() {
		return new CustomPermissionEvaluator();
	}

	/**
	 * 当资源服务器和授权服务器的 token 存储无法共享时，通过远程调用的方式，向授权服务鉴定 token，并同时获取对应的授权信息
	 * @return NimbusOpaqueTokenIntrospector
	 */
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "turbochat.security.oauth2.resource-server", name = "shared-stored-token",
		havingValue = "false")
	public OpaqueTokenIntrospector opaqueTokenIntrospector(
		OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
		OAuth2ResourceServerProperties.Opaquetoken opaqueToken = oAuth2ResourceServerProperties.getOpaqueToken();
		return new TurboChatRemoteOpaqueTokenIntrospector(opaqueToken.getIntrospectionUri(), opaqueToken.getClientId(),
			opaqueToken.getClientSecret());
	}

	/**
	 * spring-security 5.x 中开启资源服务器功能，需要的不透明令牌的支持
	 * @return OpaqueTokenAuthenticationProvider
	 */
	@Bean
	@ConditionalOnMissingBean
	public OpaqueTokenAuthenticationProvider opaqueTokenAuthenticationProvider(
		OpaqueTokenIntrospector opaqueTokenIntrospector) {
		return new OpaqueTokenAuthenticationProvider(opaqueTokenIntrospector);
	}

	/**
	 * 自定义异常处理
	 * @return AuthenticationEntryPoint
	 */
	@Bean
	@ConditionalOnMissingBean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new CustomAuthenticationEntryPoint();
	}

	/**
	 * BearTokenResolve 允许使用 url 传参，方便 ws 连接 ps: 使用 url 传参不安全，待改进
	 * @return DefaultBearerTokenResolver
	 */
	@Bean
	@ConditionalOnMissingBean
	public BearerTokenResolver bearerTokenResolver() {
		DefaultBearerTokenResolver defaultBearerTokenResolver = new DefaultBearerTokenResolver();
		defaultBearerTokenResolver.setAllowUriQueryParameter(true);
		return defaultBearerTokenResolver;
	}

}
