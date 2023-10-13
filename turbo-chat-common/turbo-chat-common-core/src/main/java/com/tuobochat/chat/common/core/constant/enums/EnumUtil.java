package com.tuobochat.chat.common.core.constant.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description 枚举工具类
 * @Date 2023/7/14 0:14
 * @Created by Alickx
 */
public class EnumUtil {

	private EnumUtil() {
	}

	public static <T extends PowerfulEnum> String getDescriptionByCode(Integer code, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants())
			.filter(e -> Objects.equals(e.getCode(), code))
			.findFirst().map(PowerfulEnum::getDescription).orElse("");
	}

	public static <T extends PowerfulEnum> T getEnumByCode(Integer code, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants())
			.filter(e -> Objects.equals(e.getCode(), code))
			.findFirst().orElse(null);
	}

	public static <T extends PowerfulEnum> List<Integer> getCodeList(Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants())
			.map(PowerfulEnum::getCode)
			.collect(Collectors.toList());
	}

}
