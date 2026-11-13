package com.tdd.skytakeout.vo;

import com.tdd.skytakeout.entity.Employee;

import java.time.LocalDateTime;

public record EmployeePageItemResponse(

        Long id,
        String name,
        String username,
        String phone,
        Integer sex,
        Integer status,
        LocalDateTime updateTime

) {
    public static EmployeePageItemResponse from(Employee employee) {
        return new EmployeePageItemResponse(
                employee.getId(),
                employee.getName(),
                employee.getUsername(),
                employee.getPhone(),
                employee.getSex(),
                employee.getStatus(),
                employee.getUpdateTime()
        );
    }
}