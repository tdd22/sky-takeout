package com.tdd.skytakeout.vo;

public record EmployeeLoginResponse(
        Long id,
        String userName,
        String name,
        String token
) {
}