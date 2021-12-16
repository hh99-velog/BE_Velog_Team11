package com.example.be_velog_team11.validator;

import com.example.be_velog_team11.exception.ErrorNotFoundUserException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorCode;
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
            throw new ErrorNotFoundUserException(ErrorCode.ERROR_DUPLICATE_EMAIL);
        }

        //닉네임 검사
        if (nickname == null || !Pattern.matches(patternNick, nickname)) {
            throw new ErrorNotFoundUserException(ErrorCode.ERROR_DUPLICATE_NICKNAME);
        }

        //비밀번호 확인
        if (password == null || !Pattern.matches(patternpw, password)) {
            throw new ErrorNotFoundUserException(ErrorCode.ERROR_USER_PASSWORD);
        }

        if (!password.equals(passwordConfirm)) {
            throw new ErrorNotFoundUserException(ErrorCode.ERROR_USER_PASSWORD);
        }
    }
}