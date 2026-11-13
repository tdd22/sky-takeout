package com.tdd.skytakeout.service;

import com.tdd.skytakeout.dto.CategoryCreateRequest;
import com.tdd.skytakeout.dto.CategoryPageRequest;
import com.tdd.skytakeout.dto.CategoryUpdateRequest;
import com.tdd.skytakeout.vo.CategoryListItemResponse;
import com.tdd.skytakeout.vo.CategoryPageItemResponse;
import com.tdd.skytakeout.vo.PageResult;

import java.util.List;

public interface CategoryService {

    void create(CategoryCreateRequest request);

    PageResult<CategoryPageItemResponse> page(
            CategoryPageRequest request
    );

    void updateStatus(Long id, Integer status);

    void update(Long id, CategoryUpdateRequest request);

    void deleteById(Long id);

    List<CategoryListItemResponse> listEnabled(Integer type);
}
