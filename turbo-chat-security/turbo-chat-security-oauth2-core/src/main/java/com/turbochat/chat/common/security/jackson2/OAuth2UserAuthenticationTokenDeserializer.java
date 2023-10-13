package com.turbochat.chat.common.security.jackson2;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turbochat.chat.common.security.authentication.OAuth2UserAuthenticationToken;
import com.turbochat.chat.common.security.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collection;

/**
 * @Description 自定义的 OAuth2UserAuthenticationToken jackson 反序列化器
 * @Date 2023/7/19 11:27
 * @Created by Alickx
 */
public class OAuth2UserAuthenticationTokenDeserializer extends JsonDeserializer<OAuth2UserAuthenticationToken> {

	private static final TypeReference<Collection<SimpleGrantedAuthority>> SIMPLE_GRANTED_AUTHORITY_SET = new TypeReference<Collection<SimpleGrantedAuthority>>() {
	};

	@Override
	public OAuth2UserAuthenticationToken deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		JsonNode jsonNode = mapper.readTree(jp);

		User principal = mapper.treeToValue(jsonNode.get("principal"), User.class);
		Collection<? extends GrantedAuthority> authorities = mapper.convertValue(jsonNode.get("authorities"),
			SIMPLE_GRANTED_AUTHORITY_SET);

		return new OAuth2UserAuthenticationToken(principal, authorities);
	}
}
