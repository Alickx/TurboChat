package com.turbochat.chat.common.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description JSON 工具类
 * @Date 2023/7/13 23:32
 * @Created by Alickx
 */
@Component
@Slf4j
public class JsonUtil {

	public static ObjectMapper objectMapper;

	public static ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	@Autowired
	public void setObjectMapper(ObjectMapper objectMapper) {
		JsonUtil.objectMapper = objectMapper;
	}

	/**
	 * Object转json字符串
	 */
	public static <T> String toJson(T obj) {
		try {
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			} else {
				return objectMapper.writeValueAsString(obj);
			}

		} catch (Exception e) {
			log.error("Parse object to String error", e);
			return null;
		}
	}

	/**
	 * json转object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String json, Class<T> clazz) {
		try {
			if (StrUtil.isEmpty(json) || clazz == null) {
				return null;
			} else if (clazz.equals(String.class)) {
				return (T) json;
			} else {
				return objectMapper.readValue(json, clazz);
			}
		} catch (IOException e) {
			log.error("Parse String to bean error", e);
			return null;
		}
	}


	/**
	 * json转集合
	 *
	 * @param <T>
	 * @param json
	 * @param typeReference <li>new TypeReference<List<User>>() {}</li>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String json, TypeReference<T> typeReference) {
		try {
			if (StrUtil.isEmpty(json) || typeReference == null) {
				return null;

			} else if (typeReference.getType().equals(String.class)) {
				return (T) json;

			} else {
				return objectMapper.readValue(json, typeReference);
			}

		} catch (IOException e) {
			log.error("Parse String to Bean error", e);
			return null;
		}
	}


	/**
	 * string转object 用于转为集合对象
	 *
	 * @param json            Json字符串
	 * @param collectionClass 被转集合的类
	 *                        <p>List.class</p>
	 * @param elementClasses  被转集合中对象类型的类
	 *                        <p>User.class</p>
	 */
	public static <T> T toBean(String json, Class<?> collectionClass, Class<?>... elementClasses) {
		try {
			JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
			return objectMapper.readValue(json, javaType);

		} catch (IOException e) {
			log.error("Parse String to Bean error", e);
			return null;
		}
	}

}
