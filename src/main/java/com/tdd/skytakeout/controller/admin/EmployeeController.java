package com.tdd.skytakeout.controller.admin;


import com.tdd.skytakeout.common.response.ApiResponse;
import com.tdd.skytakeout.dto.*;
import com.tdd.skytakeout.entity.Employee;
import com.tdd.skytakeout.security.EmployeeContext;
import com.tdd.skytakeout.security.JwtService;
import com.tdd.skytakeout.service.EmployeeService;
import com.tdd.skytakeout.vo.EmployeeDetailResponse;
import com.tdd.skytakeout.vo.EmployeeLoginResponse;

import com.tdd.skytakeout.vo.EmployeePageItemResponse;
import com.tdd.skytakeout.vo.PageResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final JwtService jwtService;

    public EmployeeController(EmployeeService employeeService, JwtService jwtService) {
        this.employeeService = employeeService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ApiResponse<EmployeeLoginResponse> login(
            @Valid @RequestBody EmployeeLoginRequest request
            ){
        Employee employee = employeeService.login(request);

        String token = jwtService.createToken(employee.getId(), employee.getUsername());

        EmployeeLoginResponse response = new EmployeeLoginResponse(employee.getId(), employee.getUsername(),
                employee.getName(), token);

        return ApiResponse.success(response);
    }

    @PostMapping
    public ApiResponse<Void> create(@Valid @RequestBody EmployeeCreateRequest request){
        employeeService.create(request);
        return ApiResponse.success(null);
    }

    @GetMapping("/page")
    public ApiResponse<PageResult<EmployeePageItemResponse>> page(@Valid @ModelAttribute EmployeePageRequest request){
        PageResult<EmployeePageItemResponse> pageResult = employeeService.page(request);
        return ApiResponse.success(pageResult);
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Void> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeStatusRequest request
    ) {
        employeeService.updateStatus(id, request.status());
        return ApiResponse.success(null);
    }

    @GetMapping("/{id}")
    public ApiResponse<EmployeeDetailResponse> findById(
            @PathVariable Long id
    ) {
        return ApiResponse.success(
                employeeService.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateRequest request
    ) {
        employeeService.update(id, request);
        return ApiResponse.success(null);
    }

}
