package com.turbochat.chat.server.constant.enums;

import com.tuobochat.chat.common.core.constant.enums.PowerfulEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Description 平台枚举
 * @Date 2023/7/14 0:11
 * @Created by Alickx
 */
@Getter
@ToString
@AllArgsConstructor
public enum PlatformEnum implements PowerfulEnum {

	WEB(10, "WEB"),
	PC(20, "PC"),
	APP(30, "APP"),


	;
	private final Integer code;

	private final String description;

}
