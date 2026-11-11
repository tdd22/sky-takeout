package com.tdd.skytakeout.mapper;


import com.tdd.skytakeout.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
