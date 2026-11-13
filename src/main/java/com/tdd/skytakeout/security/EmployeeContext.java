package com.tdd.skytakeout.security;

import com.tdd.skytakeout.service.EmployeeService;

public class EmployeeContext {

    private static final ThreadLocal<Long> CURRENT_EMPLOYEE=
            new ThreadLocal<>();

    private EmployeeContext(){

    }

    public static void setCurrentEmployeeId(Long employeeId){
        CURRENT_EMPLOYEE.set(employeeId);
    }

    public static Long getCurrentEmployeeId(){
        return CURRENT_EMPLOYEE.get();
    }

    public static void clear(){
        CURRENT_EMPLOYEE.remove();
    }
}
