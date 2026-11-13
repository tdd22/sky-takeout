package com.tdd.skytakeout.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EmployeeStatusRequest(

        @NotNull(message = "员工状态不能为空")
        @Min(value = 0, message = "员工状态只能为0或1")
        @Max(value = 1, message = "员工状态只能为0或1")
        Integer status

) {
}