package com.example.be_velog_team11.controller;

import com.example.be_velog_team11.dto.request.SignupRequestDto;
import com.example.be_velog_team11.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

/*    @GetMapping("/user/loginView")
    @ResponseBody
    public String test() {
        return "완료";
    }*/

    @ApiOperation("회원가입 요청 처리")
    @PostMapping("/user/signup")
    public void registerUser(@RequestBody SignupRequestDto signupRequestDto) {
        userService.registerUser(signupRequestDto);
    }

    @ApiOperation("ID(Email) 중복확인")
    @PostMapping("/user/id/duplicate")
    public boolean usernameCheck(@RequestBody String username) {
        return userService.usernameCheck(username);
    }

    @ApiOperation("nickname 중복확인")
    @PostMapping("/user/nickname/duplicate")
    public boolean nicknameCheck(@RequestBody String nickname) {
        return userService.nicknameCheck(nickname);
    }

}