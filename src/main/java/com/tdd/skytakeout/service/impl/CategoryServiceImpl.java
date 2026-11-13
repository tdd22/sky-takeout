package com.tdd.skytakeout.service.impl;

import com.tdd.skytakeout.common.exception.BusinessException;
import com.tdd.skytakeout.dto.CategoryCreateRequest;
import com.tdd.skytakeout.dto.CategoryPageRequest;
import com.tdd.skytakeout.dto.CategoryUpdateRequest;
import com.tdd.skytakeout.entity.Category;
import com.tdd.skytakeout.mapper.CategoryMapper;
import com.tdd.skytakeout.security.EmployeeContext;
import com.tdd.skytakeout.service.CategoryService;
import com.tdd.skytakeout.vo.CategoryListItemResponse;
import com.tdd.skytakeout.vo.CategoryPageItemResponse;
import com.tdd.skytakeout.vo.PageResult;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void create(CategoryCreateRequest request) {
        Long currentEmployeeId =
                EmployeeContext.getCurrentEmployeeId();

        Category category = new Category();
        category.setType(request.type());
        category.setName(request.name());
        category.setSort(request.sort());
        category.setStatus(1);
        category.setCreateUser(currentEmployeeId);
        category.setUpdateUser(currentEmployeeId);

        try {
            int affectedRows = categoryMapper.insert(category);

            if (affectedRows != 1) {
                throw new BusinessException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        50011,
                        "新增分类失败"
                );
            }
        } catch (DuplicateKeyException exception) {
            throw new BusinessException(
                    HttpStatus.CONFLICT,
                    40911,
                    "同类型下的分类名称已存在"
            );
        }
    }

    @Override
    public PageResult<CategoryPageItemResponse> page(
            CategoryPageRequest request
    ) {
        String name = request.name() == null
                ? null
                : request.name().trim();

        long total = categoryMapper.count(
                request.type(),
                name
        );

        if (total == 0) {
            return new PageResult<>(
                    0,
                    request.page(),
                    request.pageSize(),
                    List.of()
            );
        }

        List<Category> categories = categoryMapper.findPage(
                request.type(),
                name,
                request.pageSize(),
                request.offset()
        );

        List<CategoryPageItemResponse> records =
                categories.stream()
                        .map(CategoryPageItemResponse::from)
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

        int affectedRows = categoryMapper.updateStatus(
                id,
                status,
                currentEmployeeId
        );

        if (affectedRows == 0) {
            throw new BusinessException(
                    HttpStatus.NOT_FOUND,
                    40411,
                    "分类不存在"
            );
        }
    }

    @Override
    public void update(Long id, CategoryUpdateRequest request) {
        Category category = new Category();
        category.setId(id);
        category.setType(request.type());
        category.setName(request.name());
        category.setSort(request.sort());
        category.setUpdateUser(
                EmployeeContext.getCurrentEmployeeId()
        );

        try {
            int affectedRows = categoryMapper.update(category);

            if (affectedRows == 0) {
                throw new BusinessException(
                        HttpStatus.NOT_FOUND,
                        40411,
                        "分类不存在"
                );
            }
        } catch (DuplicateKeyException exception) {
            throw new BusinessException(
                    HttpStatus.CONFLICT,
                    40911,
                    "同类型下的分类名称已存在"
            );
        }
    }

    @Override
    public void deleteById(Long id) {
        int affectedRows = categoryMapper.deleteById(id);

        if (affectedRows == 0) {
            throw new BusinessException(
                    HttpStatus.NOT_FOUND,
                    40411,
                    "分类不存在"
            );
        }
    }

    @Override
    public List<CategoryListItemResponse> listEnabled(Integer type) {
        return categoryMapper.findByTypeAndStatus(type, 1)
                .stream()
                .map(CategoryListItemResponse::from)
                .toList();
    }
}