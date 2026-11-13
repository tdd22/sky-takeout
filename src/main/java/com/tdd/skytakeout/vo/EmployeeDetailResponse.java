package com.tdd.skytakeout.vo;

import com.tdd.skytakeout.entity.Employee;

public record EmployeeDetailResponse(

        Long id,
        String name,
        String username,
        String phone,
        Integer sex,
        String idNumber,
        Integer status

) {
    public static EmployeeDetailResponse from(Employee employee) {
        return new EmployeeDetailResponse(
                employee.getId(),
                employee.getName(),
                employee.getUsername(),
                employee.getPhone(),
                employee.getSex(),
                employee.getIdNumber(),
                employee.getStatus()
        );
    }
}