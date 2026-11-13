package com.tdd.skytakeout.service.impl;

import com.tdd.skytakeout.common.exception.BusinessException;
import com.tdd.skytakeout.dto.EmployeeCreateRequest;
import com.tdd.skytakeout.dto.EmployeeLoginRequest;
import com.tdd.skytakeout.dto.EmployeePageRequest;
import com.tdd.skytakeout.dto.EmployeeUpdateRequest;
import com.tdd.skytakeout.entity.Employee;
import com.tdd.skytakeout.mapper.EmployeeMapper;
import com.tdd.skytakeout.security.EmployeeContext;
import com.tdd.skytakeout.service.EmployeeService;
import com.tdd.skytakeout.vo.EmployeeDetailResponse;
import com.tdd.skytakeout.vo.EmployeePageItemResponse;
import com.tdd.skytakeout.vo.PageResult;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;
    private static final String DEFAULT_PASSWORD = "123456";

    public EmployeeServiceImpl(EmployeeMapper employeeMapper, PasswordEncoder passwordEncoder) {
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Employee login(EmployeeLoginRequest request){
        Employee employee = employeeMapper
                .findByUsername(request.username())
                .orElseThrow(this::invalidCredentials);

        boolean passwordMatches = passwordEncoder.matches(
                request.password(),
                employee.getPassword()
        );

        if (!passwordMatches) {
            throw invalidCredentials();
        }

        //员工状态不为1
        if (!Integer.valueOf(1).equals(employee.getStatus())) {
            throw new BusinessException(
                    HttpStatus.FORBIDDEN,
                    40301,
                    "账号已禁用"
            );
        }
        return employee;

    }

    @Override
    public void create(EmployeeCreateRequest request) {
        // 1. 提前检查用户名是否已经存在
        if (employeeMapper.findByUsername(request.username()).isPresent()) {
            throw new BusinessException(
                    HttpStatus.CONFLICT,
                    40901,
                    "用户名已存在"
            );
        }

        // 2. 从当前请求中取得执行操作的管理员 ID
        Long currentEmployeeId =
                EmployeeContext.getCurrentEmployeeId();

        // 3. 将请求 DTO 转换成数据库实体
        Employee employee = new Employee();
        employee.setName(request.name());
        employee.setUsername(request.username());
        employee.setPhone(request.phone());
        employee.setSex(request.sex());
        employee.setIdNumber(request.idNumber());

        // 4. 设置不允许客户端决定的业务字段
        employee.setPassword(
                passwordEncoder.encode(DEFAULT_PASSWORD)
        );
        employee.setStatus(1);
        employee.setCreateUser(currentEmployeeId);
        employee.setUpdateUser(currentEmployeeId);


        // 5. 写入数据库
        try {
            int affectedRows = employeeMapper.insert(employee);

            if (affectedRows != 1) {
                throw new BusinessException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        50001,
                        "新增员工失败"
                );
            }
        } catch (DuplicateKeyException exception) {
            throw new BusinessException(
                    HttpStatus.CONFLICT,
                    40901,
                    "用户名已存在"
            );
        }
    }

    @Override
    public PageResult<EmployeePageItemResponse> page(EmployeePageRequest request) {
        String name = request.name() == null
                ? null
                : request.name().trim();

        long total = employeeMapper.countByName(name);

        if (total == 0) {
            return new PageResult<>(
                    0,
                    request.page(),
                    request.pageSize(),
                    List.of()
            );
        }

        List<Employee> employees = employeeMapper.findPage(
                name,
                request.pageSize(),
                request.offset()
        );

        List<EmployeePageItemResponse> records = employees.stream()
                .map(EmployeePageItemResponse::from)
                .toList();

        return new PageResult<>(
                total,
                request.page(),
                request.pageSize(),
                records
        );

    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Long currentEmployeeId =
                EmployeeContext.getCurrentEmployeeId();

        // 防止管理员禁用自己，避免把自己锁在系统外
        if (Integer.valueOf(0).equals(status)
                && id.equals(currentEmployeeId)) {
            throw new BusinessException(
                    HttpStatus.CONFLICT,
                    40902,
                    "不能禁用当前登录账号"
            );
        }

        int affectedRows = employeeMapper.updateStatus(
                id,
                status,
                currentEmployeeId
        );

        if (affectedRows == 0) {
            throw new BusinessException(
                    HttpStatus.NOT_FOUND,
                    40401,
                    "员工不存在"
            );
        }
    }

    //不是合法用户
    private BusinessException invalidCredentials() {
        return new BusinessException(
                HttpStatus.UNAUTHORIZED,
                40101,
                "用户名或密码错误"
        );
    }

    @Override
    public EmployeeDetailResponse findById(Long id) {
        Employee employee = employeeMapper.findById(id)
                .orElseThrow(() -> new BusinessException(
                        HttpStatus.NOT_FOUND,
                        40401,
                        "员工不存在"
                ));

        return EmployeeDetailResponse.from(employee);
    }

    @Override
    public void update(Long id, EmployeeUpdateRequest request) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(request.name());
        employee.setUsername(request.username());
        employee.setPhone(request.phone());
        employee.setSex(request.sex());
        employee.setIdNumber(request.idNumber());
        employee.setUpdateUser(
                EmployeeContext.getCurrentEmployeeId()
        );

        try {
            int affectedRows = employeeMapper.update(employee);

            if (affectedRows == 0) {
                throw new BusinessException(
                        HttpStatus.NOT_FOUND,
                        40401,
                        "员工不存在"
                );
            }
        } catch (DuplicateKeyException exception) {
            throw new BusinessException(
                    HttpStatus.CONFLICT,
                    40901,
                    "用户名已存在"
            );
        }
    }



}
