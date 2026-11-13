package com.tdd.skytakeout.vo;

import com.tdd.skytakeout.entity.Category;

public record CategoryListItemResponse(

        Long id,
        Integer type,
        String name,
        Integer sort

) {
    public static CategoryListItemResponse from(Category category) {
        return new CategoryListItemResponse(
                category.getId(),
                category.getType(),
                category.getName(),
                category.getSort()
        );
    }
}