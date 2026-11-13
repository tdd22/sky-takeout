package com.tdd.skytakeout.mapper;

import com.tdd.skytakeout.entity.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;
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

    @Insert("""
        INSERT INTO employee (
            name, username, password, phone, sex,
            id_number, status, create_user, update_user
        )
        VALUES (
            #{name}, #{username}, #{password}, #{phone}, #{sex},
            #{idNumber}, #{status}, #{createUser}, #{updateUser}
        )
        """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Employee employee);


    //总数查询
    @Select("""
        <script>
        SELECT COUNT(*)
        FROM employee
        <where>
            <if test="name != null and name != ''">
                name LIKE CONCAT('%', #{name}, '%')
            </if>
        </where>
        </script>
        """)
    long countByName(@Param("name") String name);

    @Select("""
        <script>
        SELECT id,
               name,
               username,
               phone,
               sex,
               status,
               update_time
        FROM employee
        <where>
            <if test="name != null and name != ''">
                name LIKE CONCAT('%', #{name}, '%')
            </if>
        </where>
        ORDER BY update_time DESC, id DESC
        LIMIT #{limit} OFFSET #{offset}
        </script>
        """)

    List<Employee> findPage(
            @Param("name") String name,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Update("""
        UPDATE employee
        SET status = #{status},
            update_user = #{updateUser}
        WHERE id = #{id}
        """)
    int updateStatus(
            @Param("id") Long id,
            @Param("status") Integer status,
            @Param("updateUser") Long updateUser
    );

    @Select("""
        SELECT id,
               name,
               username,
               phone,
               sex,
               id_number,
               status
        FROM employee
        WHERE id = #{id}
        """)
    Optional<Employee> findById(Long id);

    @Update("""
        UPDATE employee
        SET name = #{name},
            username = #{username},
            phone = #{phone},
            sex = #{sex},
            id_number = #{idNumber},
            update_user = #{updateUser}
        WHERE id = #{id}
        """)
    int update(Employee employee);
}
