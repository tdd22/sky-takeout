package com.tdd.skytakeout.vo;

import com.tdd.skytakeout.entity.Category;

import java.time.LocalDateTime;

public record CategoryPageItemResponse(

        Long id,
        Integer type,
        String name,
        Integer sort,
        Integer status,
        LocalDateTime updateTime

) {
    public static CategoryPageItemResponse from(Category category) {
        return new CategoryPageItemResponse(
                category.getId(),
                category.getType(),
                category.getName(),
                category.getSort(),
                category.getStatus(),
                category.getUpdateTime()
        );
    }
}