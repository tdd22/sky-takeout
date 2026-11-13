package com.tdd.skytakeout.common.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApiResponseTest {

    @Test
    void shouldCreateSuccessfulResponseWithData() {
        ApiResponse<String> response = ApiResponse.success("hello");

        assertThat(response.code()).isZero();
        assertThat(response.message()).isEqualTo("success");
        assertThat(response.data()).isEqualTo("hello");
    }

    @Test
    void shouldCreateErrorResponse() {
        ApiResponse<Void> response =
                ApiResponse.error(40001, "参数错误");

        assertThat(response.code()).isEqualTo(40001);
        assertThat(response.message()).isEqualTo("参数错误");
        assertThat(response.data()).isNull();
    }
}