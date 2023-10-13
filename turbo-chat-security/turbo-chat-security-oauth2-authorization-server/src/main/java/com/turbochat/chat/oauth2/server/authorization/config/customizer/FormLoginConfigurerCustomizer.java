package com.turbochat.chat.oauth2.server.authorization.config.customizer;

import com.turbochat.chat.oauth2.server.authorization.properties.OAuth2AuthorizationServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.swing.event.HyperlinkEvent;

import java.util.Objects;

import static org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter.DEFAULT_LOGIN_PAGE_URL;

/**
 * @Description
 * @Date 2023/7/19 13:21
 * @Created by Alickx
 */
@RequiredArgsConstructor
public class FormLoginConfigurerCustomizer implements OAuth2AuthorizationServerConfigurerCustomizer {

	private final OAuth2AuthorizationServerProperties oAuth2AuthorizationServerProperties;

	private final UserDetailsService userDetailsService;

	@Override
	public void customize(OAuth2AuthorizationServerConfigurer oAuth2AuthorizationServerConfigurer,
						  HttpSecurity httpSecurity) throws Exception {

		// 是否启用表单登录
		if (oAuth2AuthorizationServerProperties.isLoginPageEnabled()) {
			String loginPage = oAuth2AuthorizationServerProperties.getLoginPage();

			if (loginPage == null) {
				httpSecurity.securityMatchers(matcher -> {
					matcher.requestMatchers(new AntPathRequestMatcher(DEFAULT_LOGIN_PAGE_URL));
				}).formLogin(Customizer.withDefaults());
			} else {
				httpSecurity.securityMatchers(matcher -> {
					matcher.requestMatchers(new AntPathRequestMatcher(loginPage));
				}).formLogin(form -> form.loginPage(loginPage).permitAll());
			}


			// 需要 userDetailsService 对应生成 DaoAuthenticationProvider
			httpSecurity.userDetailsService(userDetailsService);
		}

	}

}
