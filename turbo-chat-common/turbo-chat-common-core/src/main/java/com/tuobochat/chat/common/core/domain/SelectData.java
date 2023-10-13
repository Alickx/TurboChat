package com.tuobochat.chat.common.core.domain;

import lombok.Data;

/**
 * 下拉框所对应的视图类
 */
@Data
public class SelectData<T> {

	/**
	 * 显示的数据
	 */
	private String name;

	/**
	 * 选中获取的属性
	 */
	private Object value;

	/**
	 * 是否被选中
	 */
	private Boolean selected;

	/**
	 * 是否禁用
	 */
	private Boolean disabled;

	/**
	 * 分组标识
	 */
	private String type;

	/**
	 * 附加属性
	 */
	private T attributes;

}
