package com.tdd.skytakeout.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EmployeePageRequest(

        @NotNull(message = "页码不能为空")
        @Min(value = 1, message = "页码必须从1开始")
        Integer page,

        @NotNull(message = "每页数量不能为空")
        @Min(value = 1, message = "每页数量至少为1")
        @Max(value = 100, message = "每页数量不能超过100")
        Integer pageSize,

        String name
) {
    public int offset() {
        return (page - 1) * pageSize;
    }
}