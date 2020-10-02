package com.bootstrap.startup.http.response;

import lombok.Data;

@Data
public class BasicResponse<T> {
    private Integer status;
    private String message;
    private T data;
}
