package com.tdd.skytakeout.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeLoginRequest(

        @NotBlank(message = "用户名不能为空")
        @Size(max = 32, message = "用户名长度不能超过32个字符")
        String username,

        @NotBlank(message = "密码不能为空")
        @Size(min = 6, max = 64, message = "密码长度必须在6到64个字符之间")
        String password

) {
}