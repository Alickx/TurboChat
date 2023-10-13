package com.tuobochat.chat.common.core.constant.enums;


import com.tuobochat.chat.common.core.result.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCodeEnum implements ResultCode {

	// 通用错误
    PARAM_ERROR(1100, "参数错误"),
    UNKNOWN_ERROR(1101, "未知错误"),
    SYSTEM_ERROR(1102, "系统错误"),
	NO_PERMISSION(1103, "无权限"),
    ;

    private int code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
