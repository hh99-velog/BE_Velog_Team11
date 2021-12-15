package com.example.be_velog_team11.controller;

import com.example.be_velog_team11.dto.SignupRequestDto;
import com.example.be_velog_team11.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public void registerUser(@RequestBody SignupRequestDto signupRequestDto) {
        userService.registerUser(signupRequestDto);
    }

    // 회원 로그인 처리 (스프링 시큐리티에서 처리)
    // loginview
//    @ResponseBody
//    @GetMapping("/user/loginView")
//    public String test() {
//        return "완료";
//    }

    //Email 중복확인
    @PostMapping("/user/id/duplicate")
    public boolean usernameCheck(@RequestBody String username) {
        return userService.usernameCheck(username);
    }

    //nickname 중복확인
    @PostMapping("/user/nickname/duplicate")
    public boolean nicknameCheck(@RequestBody String nickname) {
        return userService.nicknameCheck(nickname);
    }

}