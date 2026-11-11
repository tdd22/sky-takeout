package com.tdd.skytakeout.common.response;


//使用的record 发现他在类创建里直接声明属性，像是在写方法。
//因为java会自动生成构造方法，字段访问方法，equals,hashCode和toString
//下面是三种success 作为类方法


public record ApiResponse<T>(
        int code,
        String message,
        T data
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(0, "success", data);
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(0, "success", null);
    }

    public static ApiResponse<Void> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}