package com.tdd.skytakeout.service;

import com.tdd.skytakeout.dto.EmployeeCreateRequest;
import com.tdd.skytakeout.dto.EmployeeLoginRequest;
import com.tdd.skytakeout.dto.EmployeePageRequest;
import com.tdd.skytakeout.dto.EmployeeUpdateRequest;
import com.tdd.skytakeout.entity.Employee;
import com.tdd.skytakeout.vo.EmployeeDetailResponse;
import com.tdd.skytakeout.vo.EmployeePageItemResponse;
import com.tdd.skytakeout.vo.PageResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public interface EmployeeService {

    Employee login(EmployeeLoginRequest request);

    void create(EmployeeCreateRequest request);

    PageResult<EmployeePageItemResponse> page(
            EmployeePageRequest request
    );

    void updateStatus(Long id,  Integer status);

    EmployeeDetailResponse findById(Long id);

    void update(Long id, EmployeeUpdateRequest request);


}
