package com.tdd.skytakeout.dto;

import jakarta.validation.constraints.*;

public record CategoryPageRequest(

        @NotNull(message = "页码不能为空")
        @Min(value = 1, message = "页码必须从1开始")
        Integer page,

        @NotNull(message = "每页数量不能为空")
        @Min(value = 1, message = "每页数量至少为1")
        @Max(value = 100, message = "每页数量不能超过100")
        Integer pageSize,

        @Min(value = 1, message = "分类类型只能为1或2")
        @Max(value = 2, message = "分类类型只能为1或2")
        Integer type,

        String name

) {
    public int offset() {
        return (page - 1) * pageSize;
    }
}