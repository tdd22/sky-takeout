package com.tdd.skytakeout.dto;

import jakarta.validation.constraints.*;

public record EmployeeUpdateRequest(

        @NotBlank(message = "员工姓名不能为空")
        @Size(max = 32, message = "员工姓名不能超过32个字符")
        String name,

        @NotBlank(message = "用户名不能为空")
        @Size(max = 32, message = "用户名不能超过32个字符")
        String username,

        @NotBlank(message = "手机号不能为空")
        @Pattern(
                regexp = "^1[3-9]\\d{9}$",
                message = "手机号格式不正确"
        )
        String phone,

        @NotNull(message = "性别不能为空")
        @Min(value = 0, message = "性别只能为0、1或2")
        @Max(value = 2, message = "性别只能为0、1或2")
        Integer sex,

        @NotBlank(message = "身份证号不能为空")
        @Pattern(
                regexp = "^[1-9]\\d{16}[0-9Xx]$",
                message = "身份证号格式不正确"
        )
        String idNumber
) {
}