package com.tdd.skytakeout.vo;

import java.util.List;

public record PageResult<T>(

        long total,

        int page,

        int pageSize,

        List<T> records
) {
}