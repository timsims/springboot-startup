package com.bootstrap.startup.http.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequest extends FormRequest {

    /**
     * @ImportNote 字符串用 Size 比较长度
     * 数字类型用 Min Max 比较大小
     */
    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Email
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;
}
