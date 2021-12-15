package com.example.be_velog_team11.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidator {

    public static void validateUserRegister(
            String username,
            String nickname,
            String password,
            String passwordConfirm
    ) {
        String patternId = "^(.+)@(.+)$"; //아이디: 이메일 형식(123@123)
        String patternNick = "^[a-zA-Z0-9가-힣]{3,10}$"; //닉네임: 영대소문자한글,숫자 3-10자 이내
        String patternpw = "^[a-zA-Z0-9]{4,12}$"; //비밀번호: 영대소문,숫자 4-12이내

        //아이디 검사
        if(username == null || !Pattern.matches(patternId, username)){
            throw new IllegalArgumentException("이메일 형식이 아닙니다.");
        }

        //닉네임 검사
        if (nickname == null || !Pattern.matches(patternNick, nickname)) {
            throw new IllegalArgumentException("닉네임은 문자,숫자 포함 3-10자 이내로 입력해주세요.");
        }

        //비밀번호 확인
        if (password == null || !Pattern.matches(patternpw, password)) {
            throw new IllegalArgumentException(
                    "비밀번호는 영문 대소문자, 숫자 포함 4~12자 이내로 입력해주세요.");
        }

        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}