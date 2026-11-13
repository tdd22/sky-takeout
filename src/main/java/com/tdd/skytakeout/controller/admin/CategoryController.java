package com.tdd.skytakeout.controller.admin;


import com.tdd.skytakeout.common.response.ApiResponse;
import com.tdd.skytakeout.dto.*;
import com.tdd.skytakeout.service.CategoryService;
import com.tdd.skytakeout.vo.CategoryListItemResponse;
import com.tdd.skytakeout.vo.CategoryPageItemResponse;
import com.tdd.skytakeout.vo.PageResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ApiResponse<Void> create(
            @Valid @RequestBody CategoryCreateRequest request
    ) {
        categoryService.create(request);
        return ApiResponse.success(null);
    }

    @GetMapping("/page")
    public ApiResponse<PageResult<CategoryPageItemResponse>> page(
            @Valid @ModelAttribute CategoryPageRequest request
    ) {
        return ApiResponse.success(
                categoryService.page(request)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteById(
            @PathVariable Long id
    ) {
        categoryService.deleteById(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/list")
    public ApiResponse<List<CategoryListItemResponse>> listEnabled(
            @Valid @ModelAttribute CategoryListRequest request
    ) {
        return ApiResponse.success(
                categoryService.listEnabled(request.type())
        );
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Void> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody CategoryStatusRequest request
    ) {
        categoryService.updateStatus(id, request.status());
        return ApiResponse.success(null);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryUpdateRequest request
    ) {
        categoryService.update(id, request);
        return ApiResponse.success(null);
    }
}
