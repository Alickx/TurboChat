package com.tuobochat.chat.common.core.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询参数
 */
@Data
public class PageParam {


    @Min(value = 1, message = "当前页不能小于 1")
    private long page = 1;

    @Min(value = 1, message = "每页显示条数不能小于1")
    private long size = 10;

}
