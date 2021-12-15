package com.example.be_velog_team11.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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