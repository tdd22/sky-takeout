package com.tdd.skytakeout.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CategoryStatusRequest(

        @NotNull(message = "分类状态不能为空")
        @Min(value = 0, message = "分类状态只能为0或1")
        @Max(value = 1, message = "分类状态只能为0或1")
        Integer status

) {
}