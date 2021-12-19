package com.example.be_velog_team11.controller;

import com.example.be_velog_team11.dto.request.SignupRequestDto;
import com.example.be_velog_team11.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

 /*   @ApiOperation("회원가입 요청 처리")
    @PostMapping("/user/signup")
    public Long registerUser(@RequestBody SignupRequestDto signupRequestDto) {
        log.info("user_login={}",signupRequestDto.toString());
        return userService.registerUser(signupRequestDto);
    }*/

    @ApiOperation("ID(Email) 중복확인")
    @PostMapping("/user/id/duplicate")
    public boolean usernameCheck(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.usernameCheck(signupRequestDto.getUsername());
    }

    @ApiOperation("nickname 중복확인")
    @PostMapping("/user/nickname/duplicate")
    public boolean nicknameCheck(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.nicknameCheck(signupRequestDto.getNickname());
    }

}