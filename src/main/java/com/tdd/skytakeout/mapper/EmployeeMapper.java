package com.tdd.skytakeout.mapper;

import com.tdd.skytakeout.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface EmployeeMapper {

    @Select("""
            SELECT id,
                   name,
                   username,
                   password,
                   phone,
                   sex,
                   id_number,
                   status,
                   create_time,
                   update_time,
                   create_user,
                   update_user
            FROM employee
            WHERE username = #{username}
            """)
    Optional<Employee> findByUsername(String username);
}
