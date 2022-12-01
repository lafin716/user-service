package com.lafin.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {
    @NotNull(message = "이메일은 필수입니다.")
    @Size(min = 2, message = "이메일은 최소 2글자 이상 입력해야 합니다.")
    @Email
    private String email;

    @NotNull(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상 입력해야 합니다.")
    private String password;
}
