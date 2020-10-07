package com.bootstrap.startup.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse<T> {
    private Integer status;
    private String message;
    private T data;

    public static <T> BasicResponse<T> ok(T body) {
        return new BasicResponse<>(200, "success", body);
    }
}
