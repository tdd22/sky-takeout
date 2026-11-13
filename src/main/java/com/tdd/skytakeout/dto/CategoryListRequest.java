package com.tdd.skytakeout.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CategoryListRequest(

        @NotNull(message = "分类类型不能为空")
        @Min(value = 1, message = "分类类型只能为1或2")
        @Max(value = 2, message = "分类类型只能为1或2")
        Integer type

) {
}