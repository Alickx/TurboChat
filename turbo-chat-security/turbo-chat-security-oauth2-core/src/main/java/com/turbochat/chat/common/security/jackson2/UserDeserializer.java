package com.turbochat.chat.common.security.jackson2;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.turbochat.chat.common.security.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @Description 自定义的 User jackson 反序列化器
 * @Date 2023/7/19 11:30
 * @Created by Alickx
 */
public class UserDeserializer extends JsonDeserializer<User> {

	private static final TypeReference<Collection<SimpleGrantedAuthority>> SIMPLE_GRANTED_AUTHORITY_SET = new TypeReference<Collection<SimpleGrantedAuthority>>() {
	};

	private static final TypeReference<Map<String, Object>> ATTRIBUTE_MAP = new TypeReference<Map<String, Object>>() {
	};

	@Override
	public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		JsonNode jsonNode = mapper.readTree(jp);

		JsonNode passwordNode = readJsonNode(jsonNode, "password");
		long userId = readJsonNode(jsonNode, "userId").asLong();
		String username = readJsonNode(jsonNode, "username").asText("");
		String nickname = readJsonNode(jsonNode, "nickname").asText("");
		String avatar = readJsonNode(jsonNode, "avatar").asText("");
		int status = readJsonNode(jsonNode, "status").asInt();
		long organizationId = readJsonNode(jsonNode, "organizationId").asLong();
		int type = readJsonNode(jsonNode, "type").asInt();
		String email = readJsonNode(jsonNode, "email").asText("");
		String phoneNumber = readJsonNode(jsonNode, "phoneNumber").asText("");
		int gender = readJsonNode(jsonNode, "gender").asInt();

		String password = passwordNode.asText("");
		if (passwordNode.asText(null) == null) {
			password = null;
		}

		Collection<? extends GrantedAuthority> authorities = mapper.convertValue(jsonNode.get("authorities"),
			SIMPLE_GRANTED_AUTHORITY_SET);

		Map<String, Object> attributes = mapper.convertValue(jsonNode.get("attributes"), ATTRIBUTE_MAP);

		return User.builder()
			.userId(userId)
			.username(username)
			.password(password)
			.nickname(nickname)
			.avatar(avatar)
			.status(status)
			.organizationId(organizationId)
			.email(email)
			.phoneNumber(phoneNumber)
			.gender(gender)
			.type(type)
			.authorities(authorities)
			.attributes(attributes)
			.build();
	}

	private JsonNode readJsonNode(JsonNode jsonNode, String field) {
		return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
	}
}
