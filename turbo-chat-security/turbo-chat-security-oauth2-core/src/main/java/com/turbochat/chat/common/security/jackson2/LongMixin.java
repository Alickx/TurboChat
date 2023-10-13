package com.turbochat.chat.common.security.jackson2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @Description 用于序列化/反序列化时将 Long 转换为 String
 * @Date 2023/7/19 11:13
 * @Created by Alickx
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class LongMixin {

	@JsonCreator
	LongMixin (Long value) {
	}

}
