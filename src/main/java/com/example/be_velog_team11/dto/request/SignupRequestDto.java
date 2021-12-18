package com.example.be_velog_team11.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    @NotNull
    @Email
    private String username;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @NotNull
    private String passwordConfirm;


}