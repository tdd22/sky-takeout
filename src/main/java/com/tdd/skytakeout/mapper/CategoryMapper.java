package com.tdd.skytakeout.mapper;


import com.tdd.skytakeout.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CategoryMapper {


    @Select("""
            SELECT id,
                   type,
                   name,
                   sort,
                   status,
                   create_time,
                   update_time,
                   create_user,
                   update_user
            FROM category
            WHERE type = #{type}
              AND status = #{status}
            ORDER BY sort ASC, id ASC
            """)
    List<Category> findByTypeAndStatus(@Param("type") Integer type, @Param("status") Integer status );

    @Insert("""
        INSERT INTO category (
            type,
            name,
            sort,
            status,
            create_user,
            update_user
        )
        VALUES (
            #{type},
            #{name},
            #{sort},
            #{status},
            #{createUser},
            #{updateUser}
        )
        """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Category category);


    @Select("""
        <script>
        SELECT COUNT(*)
        FROM category
        <where>
            <if test="type != null">
                type = #{type}
            </if>
            <if test="name != null and name != ''">
                AND name LIKE CONCAT('%', #{name}, '%')
            </if>
        </where>
        </script>
        """)
    long count(
            @Param("type") Integer type,
            @Param("name") String name
    );

    @Select("""
        <script>
        SELECT id,
               type,
               name,
               sort,
               status,
               update_time
        FROM category
        <where>
            <if test="type != null">
                type = #{type}
            </if>
            <if test="name != null and name != ''">
                AND name LIKE CONCAT('%', #{name}, '%')
            </if>
        </where>
        ORDER BY sort ASC, id ASC
        LIMIT #{limit} OFFSET #{offset}
        </script>
        """)
    List<Category> findPage(
            @Param("type") Integer type,
            @Param("name") String name,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Update("""
        UPDATE category
        SET status = #{status},
            update_user = #{updateUser}
        WHERE id = #{id}
        """)
    int updateStatus(
            @Param("id") Long id,
            @Param("status") Integer status,
            @Param("updateUser") Long updateUser
    );


    @Update("""
        UPDATE category
        SET type = #{type},
            name = #{name},
            sort = #{sort},
            update_user = #{updateUser}
        WHERE id = #{id}
        """)
    int update(Category category);

    @Delete("""
        DELETE FROM category
        WHERE id = #{id}
        """)
    int deleteById(Long id);


}
