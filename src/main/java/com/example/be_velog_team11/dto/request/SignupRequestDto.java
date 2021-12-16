package com.example.be_velog_team11.dto.request;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class SignupRequestDto {
    @NotNull
    private String username;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @NotNull
    private String passwordConfirm;


}