package com.tdd.skytakeout.dto;

import jakarta.validation.constraints.*;

public record CategoryCreateRequest(

        @NotNull(message = "分类类型不能为空")
        @Min(value = 1, message = "分类类型只能为1或2")
        @Max(value = 2, message = "分类类型只能为1或2")
        Integer type,

        @NotBlank(message = "分类名称不能为空")
        @Size(max = 32, message = "分类名称不能超过32个字符")
        String name,

        @NotNull(message = "排序值不能为空")
        @PositiveOrZero(message = "排序值不能小于0")
        Integer sort

) {
}